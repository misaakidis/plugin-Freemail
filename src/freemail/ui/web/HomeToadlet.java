/*
 * HomeToadlet.java
 * This file is part of Freemail
 * Copyright (C) 2011 Martin Nyhus
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package freemail.ui.web;

import java.io.IOException;
import java.net.URI;

import freenet.client.HighLevelSimpleClient;
import freenet.clients.http.LinkEnabledCallback;
import freenet.clients.http.PageMaker;
import freenet.clients.http.PageNode;
import freenet.clients.http.ToadletContext;
import freenet.clients.http.ToadletContextClosedException;
import freenet.support.HTMLNode;
import freenet.support.api.HTTPRequest;

public class HomeToadlet extends WebPage {
	private final PageMaker pageMaker;

	public HomeToadlet(HighLevelSimpleClient client, PageMaker pageMaker) {
		super(client);
		this.pageMaker = pageMaker;
	}

	@Override
	public String path() {
		return "/Freemail/";
	}

	@Override
	LinkEnabledCallback getLinkEnabledCallback() {
		return new LinkEnabledCallback() {
			@Override
			public boolean isEnabled(ToadletContext ctx) {
				return true;
			}
		};
	}

	@Override
	public void handleMethodGET(URI uri, HTTPRequest req, ToadletContext ctx) throws ToadletContextClosedException, IOException {
		PageNode page = pageMaker.getPageNode("Freemail", ctx);
		HTMLNode pageNode = page.outer;
		HTMLNode contentNode = page.content;

		addWelcomeBox(contentNode);

		writeHTMLReply(ctx, 200, "OK", pageNode.generate());
	}

	private void addWelcomeBox(HTMLNode contentNode) {
		HTMLNode welcomeBox = contentNode.addChild("div", "class", "infobox");
		welcomeBox.addChild("div", "class", "infobox-header", "Welcome to Freemail");

		HTMLNode boxContent = welcomeBox.addChild("div", "class", "infobox-content");
		boxContent.addChild("p", "Freemail is an email-like messaging system that uses the Web of " +
				"Trust plugin.");
	}

	@Override
	public void handleMethodPOST(URI uri, HTTPRequest req, ToadletContext ctx) throws ToadletContextClosedException, IOException {
		handleMethodGET(uri, req, ctx);
	}
}
