/*
 * Copyright 2010 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 * 
 *  http://aws.amazon.com/apache2.0
 * 
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.amazonaws.util;

import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FilterInputStream implementation that wraps an InputStream containing an XML
 * document, and removes the XML namespace attribute from the XML document.
 */
class NamespaceRemovingInputStream extends FilterInputStream {

    /** look ahead buffer */
    private byte[] lookAheadData = new byte[200];
    
    /** Set to true once the namespace has been removed */
    private boolean hasRemovedNamespace = false;
    
    /** Shared pattern for finding an xmlns attribute */
    private static final Pattern XMLNS_PATTERN = 
        Pattern.compile("(xmlns\\s*=\\s*\".+?\").*", Pattern.DOTALL);
    
    /**
     * Constructs a new NamespaceRemovingInputStream wrapping the specified
     * InputStream.
     * 
     * @param in
     *            The InputStream containing an XML document whose XML namespace
     *            is to be removed.
     */
    public NamespaceRemovingInputStream(InputStream in) {
        // Wrap our input stream in a buffered input stream to ensure
        // that it support mark/reset
        super(new BufferedInputStream(in));
    }

    /* (non-Javadoc)
     * @see java.io.FilterInputStream#read()
     */
    @Override
    public int read() throws IOException {
        int b = in.read();
        if (b == 'x' && !hasRemovedNamespace) {
            lookAheadData[0] = (byte)b;
            in.mark(lookAheadData.length);
            int bytesRead = in.read(lookAheadData, 1, lookAheadData.length - 1);
            in.reset();
            
            Matcher matcher = XMLNS_PATTERN.matcher(new String(lookAheadData, 0, bytesRead + 1));
            if (matcher.matches()) {
                String namespaceAttribute = matcher.group(1);
                for (int i = 0; i < namespaceAttribute.length() - 1; i++) {
                    in.read();
                }
                b = in.read();
                hasRemovedNamespace = true;
            }
        }
        
        return b;
    }

    /* (non-Javadoc)
     * @see java.io.FilterInputStream#read(byte[], int, int)
     */
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        for (int i = 0; i < len; i++) {
            int j = this.read();
            if (j == -1) {
                if (i == 0) return -1;
                return i;
            }
            
            b[i + off] = (byte)j;
        }

        return len;
    }

    /* (non-Javadoc)
     * @see java.io.FilterInputStream#read(byte[])
     */
    @Override
    public int read(byte[] b) throws IOException {
        return this.read(b, 0, b.length);
    }
}