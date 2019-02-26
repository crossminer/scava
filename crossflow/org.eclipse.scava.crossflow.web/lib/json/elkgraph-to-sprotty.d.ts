/*******************************************************************************
 * Copyright (c) 2017 Kiel University and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
import { SGraphSchema } from 'sprotty/lib';
import { ElkNode } from './elkgraph-json';
export declare class ElkGraphJsonToSprotty {
    private nodeIds;
    private edgeIds;
    private portIds;
    private labelIds;
    private sectionIds;
    transform(elkGraph: ElkNode): SGraphSchema;
    private transformElkNode(elkNode);
    private transformElkPort(elkPort);
    private transformElkLabel(elkLabel);
    private transformElkEdge(elkEdge);
    private pos(elkShape);
    private size(elkShape);
    private checkAndRememberId(e, set);
}
