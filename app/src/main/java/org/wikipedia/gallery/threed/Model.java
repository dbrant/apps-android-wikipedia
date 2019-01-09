package org.wikipedia.gallery.threed;

import android.opengl.Matrix;
import android.support.annotation.NonNull;

/*
 * Copyright 2017 Dmitry Brant. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@SuppressWarnings("checkstyle:magicnumber")
public abstract class Model {
    // Center of mass to be populated by subclasses
    private float centerMassX;
    public float centerMassX() {
        return centerMassX;
    }
    public void centerMassX(float value) {
        centerMassX = value;
    }

    private float centerMassY;
    public float centerMassY() {
        return centerMassY;
    }
    public void centerMassY(float value) {
        centerMassY = value;
    }

    private float centerMassZ;
    public float centerMassZ() {
        return centerMassZ;
    }
    public void centerMassZ(float value) {
        centerMassZ = value;
    }

    private float floorOffset;
    public float floorOffset() {
        return floorOffset;
    }
    public void floorOffset(float offset) {
        floorOffset = offset;
    }

    @NonNull private String title;

    private int glProgram = -1;
    public int glProgram() {
        return glProgram;
    }
    public void glProgram(int program) {
        glProgram = program;
    }

    private float[] modelMatrix = new float[16];
    public float[] modelMatrix() {
        return modelMatrix;
    }

    private float[] mvMatrix = new float[16];
    public float[] mvMatrix() {
        return mvMatrix;
    }

    private float[] mvpMatrix = new float[16];
    public float[] mvpMatrix() {
        return mvpMatrix;
    }

    private float maxX;
    public float maxX() {
        return maxX;
    }

    private float maxY;
    public float maxY() {
        return maxY;
    }

    private float maxZ;
    public float maxZ() {
        return maxZ;
    }

    private float minX;
    public float minX() {
        return minX;
    }

    private float minY;
    public float minY() {
        return minY;
    }

    private float minZ;
    public float minZ() {
        return minZ;
    }

    public Model() {
        maxX = Float.MIN_VALUE;
        maxY = Float.MIN_VALUE;
        maxZ = Float.MIN_VALUE;
        minX = Float.MAX_VALUE;
        minY = Float.MAX_VALUE;
        minZ = Float.MAX_VALUE;
        title = "";
    }

    public void init(float boundSize) {
        initModelMatrix(boundSize);
    }

    @NonNull public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    protected void initModelMatrix(float boundSize) {
        initModelMatrix(boundSize, 0.0f, 0.0f, 0.0f);
    }

    protected void initModelMatrix(float boundSize, float rotateX, float rotateY, float rotateZ) {
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.rotateM(modelMatrix, 0, rotateX, 1.0f, 0.0f, 0.0f);
        Matrix.rotateM(modelMatrix, 0, rotateY, 0.0f, 1.0f, 0.0f);
        Matrix.rotateM(modelMatrix, 0, rotateZ, 0.0f, 0.0f, 1.0f);
        scaleModelMatrixToBounds(boundSize);
        Matrix.translateM(modelMatrix, 0, -centerMassX, -centerMassY, -centerMassZ);
    }

    public abstract void draw(float[] viewMatrix, float[] projectionMatrix, @NonNull Light light);

    protected void adjustMaxMin(float x, float y, float z) {
        if (x > maxX) {
            maxX = x;
        }
        if (y > maxY) {
            maxY = y;
        }
        if (z > maxZ) {
            maxZ = z;
        }
        if (x < minX) {
            minX = x;
        }
        if (y < minY) {
            minY = y;
        }
        if (z < minZ) {
            minZ = z;
        }
    }

    protected void setMaxMinExtent(float extent) {
        minX = -extent;
        maxX = extent;
        minY = -extent;
        maxY = extent;
        minZ = -extent;
        maxZ = extent;
    }

    protected float getBoundScale(float boundSize) {
        float scaleX = (maxX - minX) / boundSize;
        float scaleY = (maxY - minY) / boundSize;
        float scaleZ = (maxZ - minZ) / boundSize;
        float scale = scaleX;
        if (scaleY > scale) {
            scale = scaleY;
        }
        if (scaleZ > scale) {
            scale = scaleZ;
        }
        return scale;
    }

    private void scaleModelMatrixToBounds(float boundSize) {
        float scale = getBoundScale(boundSize);
        if (scale != 0f) {
            scale = 1f / scale;
            Matrix.scaleM(modelMatrix, 0, scale, scale, scale);
        }
    }
}
