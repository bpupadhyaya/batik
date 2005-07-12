/*

   Copyright 2001,2005  The Apache Software Foundation 

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package org.apache.batik.bridge;

import org.w3c.dom.Element;

/**
 * Bridge class for the &lt;title&gt; element.
 *
 * @author <a href="mailto:vhardy@apache.org">Vincent Hardy</a>
 * @author <a href="mailto:tkormann@apache.org">Thierry Kormann</a>
 * @version $Id$
 */
public class SVGTitleElementBridge extends SVGDescriptiveElementBridge {

    /**
     * Constructs a new bridge for the &lt;title&gt; element.
     */
    public SVGTitleElementBridge() {}

    /**
     * Returns 'title'.
     */
    public String getLocalName() {
        return SVG_TITLE_TAG;
    }

    /**
     * Returns a new instance of this bridge.
     */
    public Bridge getInstance() { return new SVGTitleElementBridge(); }
}

