/*****************************************************************************
 * Copyright (C) The Apache Software Foundation. All rights reserved.        *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the Apache Software License *
 * version 1.1, a copy of which has been included with this distribution in  *
 * the LICENSE file.                                                         *
 *****************************************************************************/

package org.apache.batik.dom;

import org.w3c.dom.Node;

/**
 * This class implements the {@link org.w3c.dom.Notation} interface.
 *
 * @author <a href="mailto:stephane@hillion.org">Stephane Hillion</a>
 * @version $Id$
 */
public class GenericNotation extends AbstractNotation {
    /**
     * Is this node immutable?
     */
    protected boolean readonly;

    /**
     * Creates a new Notation object.
     */
    protected GenericNotation() {
    }

    /**
     * Creates a new Notation object.
     */
    public GenericNotation(String           name,
			   String           pubId,
			   String           sysId,
			   AbstractDocument owner) {
	ownerDocument = owner;
	setNodeName(name);
	setPublicId(pubId);
	setSystemId(sysId);
    }

    /**
     * Tests whether this node is readonly.
     */
    public boolean isReadonly() {
	return readonly;
    }

    /**
     * Sets this node readonly attribute.
     */
    public void setReadonly(boolean v) {
	readonly = v;
    }

    /**
     * Returns a new uninitialized instance of this object's class.
     */
    protected Node newNode() {
        return new GenericNotation();
    }
}
