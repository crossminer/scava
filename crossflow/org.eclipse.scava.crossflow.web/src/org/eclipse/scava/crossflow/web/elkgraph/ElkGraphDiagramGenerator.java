/**
 * Copyright (c) 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.scava.crossflow.web.elkgraph;

import java.util.List;

import org.eclipse.elk.core.IGraphLayoutEngine;
import org.eclipse.elk.core.RecursiveGraphLayoutEngine;
import org.eclipse.elk.core.math.KVector;
import org.eclipse.elk.core.math.KVectorChain;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.util.BasicProgressMonitor;
import org.eclipse.elk.graph.ElkBendPoint;
import org.eclipse.elk.graph.ElkConnectableShape;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkEdgeSection;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.ElkShape;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;
import org.eclipse.xtext.xbase.lib.StringExtensions;

import io.typefox.sprotty.api.BoundsAware;
import io.typefox.sprotty.api.Dimension;
import io.typefox.sprotty.api.IDiagramState;
import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SEdge;
import io.typefox.sprotty.api.SGraph;
import io.typefox.sprotty.api.SLabel;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelRoot;
import io.typefox.sprotty.api.SNode;
import io.typefox.sprotty.api.SPort;
import io.typefox.sprotty.server.xtext.IDiagramGenerator;

/**
 * Transforms ELK graphs into sprotty models to be transferred to clients.
 */
@SuppressWarnings("all")
public class ElkGraphDiagramGenerator implements IDiagramGenerator {
  private final IGraphLayoutEngine layoutEngine = new RecursiveGraphLayoutEngine();
  
  @Accessors(AccessorType.PUBLIC_SETTER)
  private int defaultPortSize = 5;
  
  @Accessors(AccessorType.PUBLIC_SETTER)
  private int defaultNodeSize = 1;
  
  public void layout(final ElkNode elkGraph) {
    this.applyDefaults(elkGraph);
    BasicProgressMonitor _basicProgressMonitor = new BasicProgressMonitor();
    this.layoutEngine.layout(elkGraph, _basicProgressMonitor);
  }
  
  @Override
  public SModelRoot generate(final Resource resource, final IDiagramState state, final CancelIndicator cancelIndicator) {
    final EObject originalGraph = IterableExtensions.<EObject>head(resource.getContents());
    if ((originalGraph instanceof ElkNode)) {
      final ElkNode elkGraph = EcoreUtil.<ElkNode>copy(((ElkNode)originalGraph));
      this.layout(elkGraph);
      final SGraph sgraph = new SGraph();
      sgraph.setType("graph");
      sgraph.setId(this.getId(elkGraph));
      this.processContent(elkGraph, sgraph);
      return sgraph;
    }
    return null;
  }
  
  /**
   * Transform all content of the given parent node.
   */
  protected void processContent(final ElkNode parent, final SModelElement container) {
    EList<ElkPort> _ports = parent.getPorts();
    for (final ElkPort elkPort : _ports) {
      {
        final SPort sport = new SPort();
        sport.setType("port");
        sport.setId(this.getId(elkPort));
        this.transferBounds(elkPort, sport);
        this.addChild(container, sport);
        this.processLabels(elkPort, sport);
      }
    }
    EList<ElkNode> _children = parent.getChildren();
    for (final ElkNode elkNode : _children) {
      {
        final SNode snode = new SNode();
        snode.setType("node");
        snode.setId(this.getId(elkNode));
        this.transferBounds(elkNode, snode);
        this.addChild(container, snode);
        this.processLabels(elkNode, snode);
        this.processContent(elkNode, snode);
      }
    }
    EList<ElkEdge> _containedEdges = parent.getContainedEdges();
    for (final ElkEdge elkEdge : _containedEdges) {
      if (((elkEdge.getSources().size() == 1) && (elkEdge.getTargets().size() == 1))) {
        final SEdge sedge = new SEdge();
        sedge.setType("edge");
        sedge.setId(this.getId(elkEdge));
        sedge.setSourceId(this.getId(IterableExtensions.<ElkConnectableShape>head(elkEdge.getSources())));
        sedge.setTargetId(this.getId(IterableExtensions.<ElkConnectableShape>head(elkEdge.getTargets())));
        this.transferEdgeLayout(elkEdge, sedge);
        this.addChild(container, sedge);
        this.processLabels(elkEdge, sedge);
      } else {
        EList<ElkConnectableShape> _sources = elkEdge.getSources();
        for (final ElkConnectableShape source : _sources) {
          EList<ElkConnectableShape> _targets = elkEdge.getTargets();
          for (final ElkConnectableShape target : _targets) {
            {
              final SEdge sedge_1 = new SEdge();
              sedge_1.setType("edge");
              String _id = this.getId(elkEdge);
              String _plus = (_id + "_");
              String _id_1 = this.getId(source);
              String _plus_1 = (_plus + _id_1);
              String _plus_2 = (_plus_1 + "_");
              String _id_2 = this.getId(target);
              String _plus_3 = (_plus_2 + _id_2);
              sedge_1.setId(_plus_3);
              sedge_1.setSourceId(this.getId(source));
              sedge_1.setTargetId(this.getId(target));
              this.transferEdgeLayout(elkEdge, sedge_1);
              this.addChild(container, sedge_1);
              this.processLabels(elkEdge, sedge_1);
            }
          }
        }
      }
    }
  }
  
  /**
   * Transform all labels of the given graph element.
   */
  protected void processLabels(final ElkGraphElement element, final SModelElement container) {
    EList<ElkLabel> _labels = element.getLabels();
    for (final ElkLabel elkLabel : _labels) {
      {
        final SLabel slabel = new SLabel();
        slabel.setType("label");
        slabel.setId(this.getId(elkLabel));
        String _text = elkLabel.getText();
        String _plus = (_text + " [ ");
        double _random = Math.random();
        String _plus_1 = (_plus + Double.valueOf(_random));
        String _plus_2 = (_plus_1 + " ]");
        slabel.setText(_plus_2);
        System.out.println(slabel.getText());
        this.transferBounds(elkLabel, slabel);
        this.addChild(container, slabel);
        this.processLabels(elkLabel, slabel);
      }
    }
  }
  
  /**
   * Apply default layout information to all contents of the given parent node.
   */
  private void applyDefaults(final ElkNode parent) {
    EList<ElkPort> _ports = parent.getPorts();
    for (final ElkPort port : _ports) {
      {
        double _width = port.getWidth();
        boolean _lessEqualsThan = (_width <= 0);
        if (_lessEqualsThan) {
          port.setWidth(this.defaultPortSize);
        }
        double _height = port.getHeight();
        boolean _lessEqualsThan_1 = (_height <= 0);
        if (_lessEqualsThan_1) {
          port.setHeight(this.defaultPortSize);
        }
        this.computeLabelSizes(port);
      }
    }
    EList<ElkNode> _children = parent.getChildren();
    for (final ElkNode node : _children) {
      {
        double _width = node.getWidth();
        boolean _lessEqualsThan = (_width <= 0);
        if (_lessEqualsThan) {
          node.setWidth(this.defaultNodeSize);
        }
        double _height = node.getHeight();
        boolean _lessEqualsThan_1 = (_height <= 0);
        if (_lessEqualsThan_1) {
          node.setHeight(this.defaultNodeSize);
        }
        this.computeLabelSizes(node);
        this.applyDefaults(node);
      }
    }
    EList<ElkEdge> _containedEdges = parent.getContainedEdges();
    for (final ElkEdge edge : _containedEdges) {
      this.computeLabelSizes(edge);
    }
  }
  
  /**
   * Compute sizes for all labels of an element. <em>Note:</em> Sizes are hard-coded here, so don't expect
   * the result to be rendered properly on all clients!
   */
  private void computeLabelSizes(final ElkGraphElement element) {
    EList<ElkLabel> _labels = element.getLabels();
    for (final ElkLabel label : _labels) {
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(label.getText());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        double _width = label.getWidth();
        boolean _lessEqualsThan = (_width <= 0);
        if (_lessEqualsThan) {
          int _length = label.getText().length();
          int _multiply = (_length * 9);
          label.setWidth(_multiply);
        }
        double _height = label.getHeight();
        boolean _lessEqualsThan_1 = (_height <= 0);
        if (_lessEqualsThan_1) {
          label.setHeight(16);
        }
      }
    }
  }
  
  /**
   * Add a child element to the sprotty model.
   */
  private void addChild(final SModelElement container, final SModelElement child) {
    List<SModelElement> _children = container.getChildren();
    boolean _tripleEquals = (_children == null);
    if (_tripleEquals) {
      container.setChildren(CollectionLiterals.<SModelElement>newArrayList());
    }
    container.getChildren().add(child);
  }
  
  /**
   * Transfer bounds to a sprotty model element.
   */
  private void transferBounds(final ElkShape shape, final BoundsAware bounds) {
    double _x = shape.getX();
    double _y = shape.getY();
    Point _point = new Point(_x, _y);
    bounds.setPosition(_point);
    if (((shape.getWidth() > 0) || (shape.getHeight() > 0))) {
      double _width = shape.getWidth();
      double _height = shape.getHeight();
      Dimension _dimension = new Dimension(_width, _height);
      bounds.setSize(_dimension);
    }
  }
  
  /**
   * Transfer an edge layout to a sprotty edge.
   */
  private void transferEdgeLayout(final ElkEdge elkEdge, final SEdge sEdge) {
    sEdge.setRoutingPoints(CollectionLiterals.<Point>newArrayList());
    EList<ElkEdgeSection> _sections = elkEdge.getSections();
    for (final ElkEdgeSection section : _sections) {
      {
        List<Point> _routingPoints = sEdge.getRoutingPoints();
        double _startX = section.getStartX();
        double _startY = section.getStartY();
        Point _point = new Point(_startX, _startY);
        _routingPoints.add(_point);
        EList<ElkBendPoint> _bendPoints = section.getBendPoints();
        for (final ElkBendPoint bendPoint : _bendPoints) {
          List<Point> _routingPoints_1 = sEdge.getRoutingPoints();
          double _x = bendPoint.getX();
          double _y = bendPoint.getY();
          Point _point_1 = new Point(_x, _y);
          _routingPoints_1.add(_point_1);
        }
        List<Point> _routingPoints_2 = sEdge.getRoutingPoints();
        double _endX = section.getEndX();
        double _endY = section.getEndY();
        Point _point_2 = new Point(_endX, _endY);
        _routingPoints_2.add(_point_2);
      }
    }
    final KVectorChain junctionPoints = elkEdge.<KVectorChain>getProperty(CoreOptions.JUNCTION_POINTS);
    final Procedure2<KVector, Integer> _function = (KVector point, Integer index) -> {
      final SNode sJunction = new SNode();
      sJunction.setType("junction");
      String _id = this.getId(elkEdge);
      String _plus = (_id + "_j");
      String _plus_1 = (_plus + index);
      sJunction.setId(_plus_1);
      Point _point = new Point(point.x, point.y);
      sJunction.setPosition(_point);
      this.addChild(sEdge, sJunction);
    };
    IterableExtensions.<KVector>forEach(junctionPoints, _function);
  }
  
  /**
   * Compute a unique identifier for the given element.
   */
  private String getId(final ElkGraphElement element) {
    final EObject container = element.eContainer();
    if ((container instanceof ElkGraphElement)) {
      String identifier = element.getIdentifier();
      if ((identifier == null)) {
        final EStructuralFeature feature = element.eContainingFeature();
        Object _eGet = ((ElkGraphElement)container).eGet(feature);
        final List<? extends ElkGraphElement> list = ((List<? extends ElkGraphElement>) _eGet);
        String _name = feature.getName();
        String _plus = (_name + "#");
        int _indexOf = list.indexOf(element);
        String _plus_1 = (_plus + Integer.valueOf(_indexOf));
        identifier = _plus_1;
      }
      String _id = this.getId(((ElkGraphElement)container));
      String _plus_2 = (_id + ".");
      return (_plus_2 + identifier);
    } else {
      String _elvis = null;
      String _identifier = element.getIdentifier();
      if (_identifier != null) {
        _elvis = _identifier;
      } else {
        _elvis = "graph";
      }
      return _elvis;
    }
  }
  
  public void setDefaultPortSize(final int defaultPortSize) {
    this.defaultPortSize = defaultPortSize;
  }
  
  public void setDefaultNodeSize(final int defaultNodeSize) {
    this.defaultNodeSize = defaultNodeSize;
  }
}
