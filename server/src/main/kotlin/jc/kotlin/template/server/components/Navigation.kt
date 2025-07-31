package jc.kotlin.template.server.components

import jc.kotlin.template.server.routes.Page
import kotlinx.html.*

// TODO nav not responsive at all yet
fun FlowContent.navigation(currentPage: Page, navPages: Set<Page>) {
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
            "border-zinc-800"
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
                div {
                    classes = setOf(
                        "absolute",
                        "-bottom-0.5",
                        "left-0",
                        "w-5",
                        "h-px",
                        "bg-mint"
                    )
                }
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
                        "absolute",
                        "-bottom-2",
                        "left-0",
                        "w-0",
                        "h-px",
                        "bg-peach",
                        "transition-all",
                        "duration-300",
                        "group-hover:w-full"
                    )

                    navPages.map {
                        li {
                            a(href = it.path) {
                                classes = navLiStyles
                                +it.title
                                div(afterNavLiStyles.joinToString(" "))

                                if (currentPage == it) {
                                    div {
                                        classes = setOf(
                                            "absolute",
                                            "-bottom-2",
                                            "left-0",
                                            "w-5",
                                            "h-px",
                                            "bg-peach"
                                        )
                                    }
                                }
                            }
                        }
                    }
                    li {
                        a("/logout") {
                            classes = navLiStyles
                            +"Logout"
                            div(afterNavLiStyles.joinToString(" "))
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

//nav {
//        classes = setOf(
//            "fixed",
//            "top-0",
//            "left-0",
//            "right-0",
//            "bg-black/90",
//            "backdrop-blur-xl",
//            "z-[900]",
//            "border-b",
//            "border-gray-800"
//        )
//
////        val navBeforeStyles = setOf(
////            "absolute",
////            "top-0",
////            "left-0",
////            "right-0",
////            "h-px",
////            "bg-gradient-to-r",
////            "from-transparent",
////            "via-gray-200/[0.27]",
////            "to-transparent"
////        )
////        div(navBeforeStyles.joinToString(" "))
//
////        val defaultClasses = setOf(
////            "p-1",
////            "px-8",
////            "border-b-2",
////            "border-gray-200",
////            "hover:bg-gray-100",
////            "active:bg-gray-200"
////        )
////        val isCurrentClasses = setOf("font-bold", "border-gray-400")
//
//        val defaultClasses = setOf(
//            "text-gray-300",
//            "no-underline",
//            "font-light",
//            "transition-all",
//            "duration-300",
//            "ease-in-out",
//            "relative",
//            "hover:text-white",
//            "after:content-['']",
//            "after:absolute",
//            "after:-bottom-2",
//            "after:left-0",
//            "after:w-0",
//            "after:h-px",
//            "after:bg-gradient-to-r",
//            "after:from-orange-200",
//            "after:to-yellow-200",
//            "after:transition-all",
//            "after:duration-300",
//            "after:ease-in-out",
//            "hover:after:w-full"
//        )
//
//        ul {
//            classes = setOf(
//                "flex", "gap", "list-none"
//            )
//            navPages.map {
////                val isCurrent = currentPage == it
//                li {
//                    a(it.path) {
////                        classes = if (isCurrent) defaultClasses + isCurrentClasses
////                        else defaultClasses
//                        classes = defaultClasses
//
//                        +it.title
//                    }
//                }
//            }
//            li {
//                a("/logout") {
//                    classes = defaultClasses
//                    +"Logout"
//                }
//            }
//        }
//    }
}