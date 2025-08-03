package jc.kotlin.template.server.components

import jc.kotlin.template.server.routes.Page
import jc.kotlin.template.server.utility.Color
import kotlinx.html.*

fun FlowContent.navigation(currentPage: Page, navPages: Set<Page>) {
    script {
        src = "/static/jc_nav.js"
    }

    header {
        classes = setOf(
            "fixed",
            "top-0",
            "left-0",
            "right-0",
            "py-6",
            "bg-zinc-950/90",
            "backdrop-blur-xl",
            "z-50",
            "border-b",
            "border-zinc-800",
//            "shadow-md",
//            "shadow-zinc-800/50"
        )

        div {
            classes = setOf(
                "flex",
                "items-center",
                "justify-between",
                "px-8",
                "max-w-6xl",
                "mx-auto"
            )

            a(href = "/welcome") {
                classes = setOf(
                    "text-xl",
                    "font-normal",
                    "text-white",
                    "no-underline",
                    "relative",
                    "group"
                )
                +"Template Project"
                titleAccent()
            }

            nav {
                classes = setOf("hidden", "md:block")

                ul {
                    classes = setOf("flex", "gap-12", "list-none")

                    val navLiStyles = setOf(
                        "text-zinc-400",
                        "no-underline",
                        "font-light",
                        "transition-all",
                        "duration-300",
                        "relative",
                        "hover:text-white",
                        "group"
                    )
                    val afterNavLiStyles = setOf(
                        "transition-all",
                        "duration-300",
                        "group-hover:w-full",
                        "group-hover:to-peach"
                    )

                    navPages.map {
                        li {
                            a(href = it.path) {
                                classes = navLiStyles
                                +it.title

                                if (currentPage == it) {
                                    indicatorDot(props = AccentProps(color = Color.Mint))
                                } else {
                                    titleAccent(
                                        props = AccentProps(
                                            color = Color.Peach,
                                            classes = afterNavLiStyles
                                        )
                                    )
                                }
                            }
                        }
                    }
                    li {
                        a("/logout") {
                            classes = navLiStyles
                            +"Logout"
                            titleAccent(
                                props = AccentProps(
                                    color = Color.Peach,
                                    classes = afterNavLiStyles
                                )
                            )
                        }
                    }
                }
            }

            button {
                classes =
                    setOf("md:hidden", "bg-transparent", "border-none", "text-zinc-400", "text-2xl", "cursor-pointer")
                onClick = "toggleMobileNav()"
                +"☰"
            }
        }
    }

    // Mobile Navigation
    div {
        classes = setOf(
            "fixed",
            "top-0",
            "-right-full",
            "w-full",
            "h-screen",
            "bg-black/[0.98]",
            "backdrop-blur-xl",
            "z-[1000]",
            "transition-all",
            "duration-[400ms]",
            "ease-out",
            "flex",
            "flex-col",
            "justify-center",
            "items-center",
            "mobile-nav"
        )
        id = "mobileNav"

        button {
            classes = setOf(
                "absolute",
                "top-8",
                "right-8",
                "bg-transparent",
                "border-none",
                "text-zinc-300",
                "text-3xl",
                "cursor-pointer"
            )
            onClick = "toggleMobileNav()"
            +"×"
        }

        ul {
            classes = setOf("list-none", "text-center")

            navPages.map {
                li {

                    classes = setOf("mb-8", "relative")

                    a(href = it.path) {
                        val navLinkClass = setOf(
                            "no-underline",
                            "text-2xl",
                            "font-light",
                            "transition-colors",
                            "duration-300",
                            "hover:text-mint"
                        )
                        if (currentPage == it) {
                            classes = navLinkClass + setOf("text-mint")
                            indicatorDot(props = AccentProps(color = Color.Mint))
                        } else {
                            classes = navLinkClass + setOf("text-zine-300")
                        }

                        +it.title
                    }
                }
            }
            li {
                classes = setOf("mb-8")
                a(href = "/logout") {
                    classes = setOf(
                        "text-zinc-300",
                        "no-underline",
                        "text-2xl",
                        "font-light",
                        "transition-colors",
                        "duration-300",
                        "hover:text-mint"
                    )
                    +"Logout"
                }
            }

        }
    }
}