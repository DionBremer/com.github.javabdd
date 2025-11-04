//////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2025 Contributors to the Eclipse Foundation
//
// See the NOTICE file(s) distributed with this work for additional
// information regarding copyright ownership.
//
// This program and the accompanying materials are made available
// under the terms of the MIT License which is available at
// https://opensource.org/licenses/MIT
//
// SPDX-License-Identifier: MIT
//////////////////////////////////////////////////////////////////////////////

package com.github.javabdd;

import java.util.ArrayList;
import java.util.List;

public class SaturationPath {
    private final List<SaturationDirection> path;

    public SaturationPath() {
        this.path = new ArrayList<>();
    }

    public SaturationPath(SaturationPath other) {
        this.path = new ArrayList<>();
        for (SaturationDirection dir : other.path) {
            this.path.add(dir);
        }
    }

    public SaturationPath add(SaturationDirection direction) {
        SaturationPath result = new SaturationPath();
        for (SaturationDirection dir : this.path) {
            result.path.add(dir);
        }
        result.path.add(direction);
        return result;
    }

    public SaturationDirection getNext() {
        return path.getFirst();
    }

    public SaturationPath getTail() {
        SaturationPath result = new SaturationPath();
        for (int i = 1; i < path.size(); i++) {
            result.path.add(path.get(i));
        }
        return result;
    }

    public boolean isEmpty() {
        return path.isEmpty();
    }

    @Override
    public String toString() {
        return path.stream().map(dir -> dir.toString()).reduce("", String::concat);
    }
}
