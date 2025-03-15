package jc.kotlin.template.server.components

import kotlinx.html.HTMLTag

fun HTMLTag.hxPost(to: String) {
    this.attributes["hx-post"] = to
}

fun HTMLTag.hxGet(from: String) {
    this.attributes["hx-get"] = from
}

fun HTMLTag.hxSwap(with: String = "innerHTML") {
    this.attributes["hx-swap"] = with
}

fun HTMLTag.hxTrigger(by: String = "click") {
    this.attributes["hx-trigger"] = by
}

fun HTMLTag.hxTarget(target: String = "#content") {
    this.attributes["hx-target"] = target
}

fun HTMLTag.hxIndicator(target: String = "#content") {
    this.attributes["hx-indicator"] = target
}

fun HTMLTag.hxSwapOob() {
    this.attributes["hx-swap-oob"] = "true"
}

fun HTMLTag.hxSync(on: String = "closest form:abort") {
    this.attributes["hx-sync"] = on
}
