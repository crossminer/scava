/*******************************************************************************
 * Copyright (c) 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
import { SNode, RectangularNode, RectangularPort, SEdge } from "sprotty/lib";
export declare class ElkNode extends RectangularNode {
    hasFeature(feature: symbol): boolean;
}
export declare class ElkPort extends RectangularPort {
    hasFeature(feature: symbol): boolean;
}
export declare class ElkEdge extends SEdge {
    hasFeature(feature: symbol): boolean;
}
export declare class ElkJunction extends SNode {
    hasFeature(feature: symbol): boolean;
}
