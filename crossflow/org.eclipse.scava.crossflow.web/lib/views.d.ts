import { VNode } from "snabbdom/vnode";
import { RenderingContext, SEdge, IView, PolylineEdgeView, RectangularNodeView, CircularNodeView, Point, SLabel } from "sprotty/lib";
import { ElkNode, ElkPort, ElkJunction } from "./sprotty-model";
export declare class ElkNodeView extends RectangularNodeView {
    render(node: ElkNode, context: RenderingContext): VNode;
}
export declare class ElkPortView extends RectangularNodeView {
    render(port: ElkPort, context: RenderingContext): VNode;
}
export declare class ElkEdgeView extends PolylineEdgeView {
    protected renderLine(edge: SEdge, segments: Point[], context: RenderingContext): VNode;
    protected renderAdditionals(edge: SEdge, segments: Point[], context: RenderingContext): VNode[];
}
export declare class ElkLabelView implements IView {
    render(label: SLabel, context: RenderingContext): VNode;
}
export declare class JunctionView extends CircularNodeView {
    render(node: ElkJunction, context: RenderingContext): VNode;
    protected getRadius(node: ElkJunction): number;
}
