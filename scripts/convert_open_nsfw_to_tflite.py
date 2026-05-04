#!/usr/bin/env python3
"""Convert OpenNSFW (Yahoo architecture) model to TensorFlow Lite.

This script uses the opennsfw2 package, which provides a Keras implementation
of Yahoo's Open NSFW model and pre-trained Yahoo-compatible weights.

Example:
  python scripts/convert_open_nsfw_to_tflite.py \
      --output app/src/main/assets/nsfw_classifier.tflite
"""

from __future__ import annotations

import argparse
from pathlib import Path
import tempfile

import opennsfw2 as n2
import tensorflow as tf


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Convert OpenNSFW model to TFLite")
    parser.add_argument(
        "--output",
        type=Path,
        required=True,
        help="Output .tflite path",
    )
    parser.add_argument(
        "--quantize",
        action="store_true",
        help="Enable dynamic range quantization (smaller model, potential accuracy/latency tradeoff)",
    )
    return parser.parse_args()


def main() -> None:
    args = parse_args()
    args.output.parent.mkdir(parents=True, exist_ok=True)

    # Build model with Yahoo-compatible pre-trained weights.
    model = n2.make_open_nsfw_model()

    # Export to SavedModel first to avoid Keras->TFLite conversion issues with
    # resource variables in BatchNorm layers on some TF/Keras combinations.
    with tempfile.TemporaryDirectory() as temp_dir:
        model.export(temp_dir)
        converter = tf.lite.TFLiteConverter.from_saved_model(temp_dir)
        converter.experimental_enable_resource_variables = True
        converter.target_spec.supported_ops = [
            tf.lite.OpsSet.TFLITE_BUILTINS,
            tf.lite.OpsSet.SELECT_TF_OPS,
        ]
        if args.quantize:
            converter.optimizations = [tf.lite.Optimize.DEFAULT]

        tflite_model = converter.convert()
    args.output.write_bytes(tflite_model)

    interpreter = tf.lite.Interpreter(model_content=tflite_model)
    interpreter.allocate_tensors()
    input_details = interpreter.get_input_details()[0]
    output_details = interpreter.get_output_details()[0]

    print(f"Wrote: {args.output}")
    print(f"Input : shape={input_details['shape']} dtype={input_details['dtype']}")
    print(f"Output: shape={output_details['shape']} dtype={output_details['dtype']}")


if __name__ == "__main__":
    main()
