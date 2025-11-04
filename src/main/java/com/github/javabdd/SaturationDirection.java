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

public enum SaturationDirection {
    LOW,
    HIGH;

    @Override
    public String toString() {
        return (this == LOW) ? "l" : "h";
    }
}