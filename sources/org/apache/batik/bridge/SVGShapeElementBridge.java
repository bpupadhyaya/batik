/*****************************************************************************
 * Copyright (C) The Apache Software Foundation. All rights reserved.        *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the Apache Software License *
 * version 1.1, a copy of which has been included with this distribution in  *
 * the LICENSE file.                                                         *
 *****************************************************************************/

package org.apache.batik.bridge;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import java.io.StringReader;

import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.BridgeMutationEvent;
import org.apache.batik.bridge.GraphicsNodeBridge;
import org.apache.batik.bridge.IllegalAttributeValueException;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.gvt.GraphicsNodeRenderContext;
import org.apache.batik.gvt.ShapeNode;
import org.apache.batik.gvt.ShapePainter;
import org.apache.batik.ext.awt.image.renderable.Clip;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.gvt.filter.Mask;
import org.apache.batik.parser.ParseException;
import org.apache.batik.bridge.resources.Messages;
import org.apache.batik.util.SVGConstants;
import org.apache.batik.util.UnitProcessor;

import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSPrimitiveValue;

/**
 * A factory for the SVG elements that represents a shape.
 *
 * @author <a href="mailto:Thierry.Kormann@sophia.inria.fr">Thierry Kormann</a>
 * @author <a href="mailto:cjolif@ilog.fr">Christophe Jolif</a>
 * @version $Id$
 */
public abstract class SVGShapeElementBridge implements GraphicsNodeBridge,
                                                       SVGConstants {

    public GraphicsNode createGraphicsNode(BridgeContext ctx, Element element){
        SVGElement svgElement = (SVGElement) element;
        CSSStyleDeclaration cssDecl = CSSUtilities.getComputedStyle(element);
        UnitProcessor.Context uctx
            = new DefaultUnitProcessorContext(ctx,
                                              cssDecl);
        ShapeNode node = new ShapeNode();
        // Initialize the transform
        String transformStr = element.getAttributeNS(null, ATTR_TRANSFORM);
        if (transformStr.length() > 0) {
            AffineTransform at = SVGUtilities.convertAffineTransform(transformStr);
            node.setTransform(at);
        }
        // Initialize the shape of the ShapeNode
        buildShape(ctx, svgElement, node, cssDecl, uctx);

        return node;
    }

    public void buildGraphicsNode(GraphicsNode gn,
                                  BridgeContext ctx,
                                  Element element) {
        ShapeNode node = (ShapeNode)gn;

        SVGElement svgElement = (SVGElement) element;
        CSSStyleDeclaration cssDecl = CSSUtilities.getComputedStyle(element);
        UnitProcessor.Context uctx
            = new DefaultUnitProcessorContext(ctx,
                                              cssDecl);

        // Initialize the style properties
        ShapePainter painter = convertPainter(svgElement,
                                              node,
                                              cssDecl,
                                              uctx, ctx);

        node.setShapePainter(painter);

        // Set node composite
        CSSPrimitiveValue opacityVal =
            (CSSPrimitiveValue)cssDecl.getPropertyCSSValue(ATTR_OPACITY);
        Composite composite =
            CSSUtilities.convertOpacityToComposite(opacityVal);
        node.setComposite(composite);

        // Set node filter
        Filter filter = CSSUtilities.convertFilter(element, node, ctx);
        node.setFilter(filter);

        // Set the node mask
        Mask mask = CSSUtilities.convertMask(element, node, ctx);
        node.setMask(mask);

        // Set the node clip
        Clip clip = CSSUtilities.convertClipPath(element, node, ctx);
        node.setClip(clip);

        // <!> TODO only when binding is enabled
        BridgeEventSupport.addDOMListener(ctx, element);
        ctx.bind(element, node);
    }

    protected ShapePainter convertPainter(SVGElement svgElement,
                                          ShapeNode node,
                                          CSSStyleDeclaration cssDecl,
                                          UnitProcessor.Context uctx,
                                          BridgeContext ctx){
        return CSSUtilities.convertStrokeAndFill(node.getShape(), svgElement, node,
                                                 ctx, cssDecl, uctx);
    }

    public void update(BridgeMutationEvent evt) {
    }

    public boolean isContainer() {
        return false;
    }

    /**
     * Creates the shape depending on the specified context and element.
     */
    protected abstract void buildShape(BridgeContext ctx,
                                       SVGElement svgElement,
                                       ShapeNode node,
                                       CSSStyleDeclaration decl,
                                       UnitProcessor.Context uctx);
}
