/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.components.toolbar

import android.view.View
import mozilla.components.browser.session.SelectionAwareSessionObserver
import mozilla.components.browser.session.Session
import mozilla.components.browser.session.SessionManager
import mozilla.components.browser.toolbar.BrowserToolbar
import mozilla.components.concept.engine.manifest.WebAppManifest

class MenuPresenter(
    private val menuToolbar: BrowserToolbar,
    sessionManager: SessionManager,
    private val sessionId: String? = null
) : SelectionAwareSessionObserver(sessionManager), View.OnAttachStateChangeListener {

    fun start() {
        observeIdOrSelected(sessionId)
        menuToolbar.addOnAttachStateChangeListener(this)
    }

    /** Redraw the refresh/stop button */
    override fun onLoadingStateChanged(session: Session, loading: Boolean) {
        invalidateActions()
    }

    /** Redraw the back and forward buttons */
    override fun onNavigationStateChanged(session: Session, canGoBack: Boolean, canGoForward: Boolean) {
        invalidateActions()
    }

    /** Redraw the install web app button */
    override fun onWebAppManifestChanged(session: Session, manifest: WebAppManifest?) {
        invalidateActions()
    }

    fun invalidateActions() {
        menuToolbar.invalidateActions()
    }

    override fun onViewDetachedFromWindow(v: View?) {
        menuToolbar.onStop()
    }

    override fun onViewAttachedToWindow(v: View?) {
        // no-op
    }
}
