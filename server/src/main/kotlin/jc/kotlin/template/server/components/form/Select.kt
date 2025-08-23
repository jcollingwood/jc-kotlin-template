package jc.kotlin.template.server.components.form

import jc.kotlin.template.server.components.iconSpan
import kotlinx.html.CommonAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.classes

// TODO add color options
/*
    * Custom Select Dropdown
        /* Custom Select Dropdown */
        .select-wrapper {
            position: relative;
            margin-bottom: 2rem;
        }

        .form-select {
            width: 100%;
            max-width: 400px;
            padding: 1rem 2rem 1rem 0;
            background: transparent;
            border: none;
            border-bottom: 1px solid #333;
            color: #f5f5f5;
            font-size: 1rem;
            font-weight: 300;
            transition: all 0.3s ease;
            cursor: pointer;
            -webkit-appearance: none;
            -moz-appearance: none;
            appearance: none;
        }

        .form-select:focus {
            outline: none;
            border-bottom-color: #ffb3ba;
        }

        .form-select:valid {
            color: #f5f5f5;
        }

        .form-select option {
            background: #1a1a1a;
            color: #f5f5f5;
            padding: 0.5rem;
            border: none;
        }

        .form-select option:hover {
            background: #2a2a2a;
        }

        .select-wrapper .form-label {
            position: absolute;
            left: 0;
            top: 1rem;
            color: #666;
            font-size: 1rem;
            font-weight: 300;
            transition: all 0.3s ease;
            pointer-events: none;
        }

        .form-select:focus + .form-label,
        .form-select:valid + .form-label {
            top: -0.5rem;
            font-size: 0.8rem;
            color: #ffb3ba;
        }

        .select-arrow {
            position: absolute;
            right: 0;
            top: 50%;
            transform: translateY(-50%);
            pointer-events: none;
            transition: all 0.3s ease;
        }

        .form-select:focus ~ .select-arrow {
            transform: translateY(-50%) rotate(180deg);
        }

        .form-select:focus ~ .select-arrow svg path {
            stroke: #ffb3ba;
        }

       * HTML:
            <div class="select-wrapper">
                <select class="form-select" required>
                    <option value="" disabled selected hidden></option>
                    <option value="us">United States</option>
                    <option value="ca">Canada</option>
                    <option value="uk">United Kingdom</option>
                    <option value="au">Australia</option>
                    <option value="de">Germany</option>
                    <option value="fr">France</option>
                    <option value="jp">Japan</option>
                </select>
                <label class="form-label">Country</label>
                <div class="select-arrow">
                    <svg width="12" height="8" viewBox="0 0 12 8" fill="none">
                        <path d="M1 1L6 6L11 1" stroke="#666" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                </div>
            </div>
 */

fun <T : CommonAttributeGroupFacade> T.selectWrapper(): T {
    classes = setOf(
        "select-wrapper",
        "relative",
        "mb-8"
    )
    return this
}

fun <T : CommonAttributeGroupFacade> T.formSelect(): T {
    classes = setOf(
        "form-select",
        "w-full",
        "py-4",
        "px-2",
        "bg-transparent",
        "border-b",
        "border-gray-600",
        "text-gray-100",
        "transition-all",
        "duration-300",
        "ease-in-out",
        "cursor-pointer",
        "appearance-none",
        // focus styles
        "focus:outline-none",
        "focus:border-mint",
        // valid styles
        "valid:focus:border-mint",
    )
    return this
}

fun <T : CommonAttributeGroupFacade> T.formSelectOption(): T {
    classes = setOf(
        "bg-dark-800",
        "text-gray-100",
        "p-2",
        "border-none",
        // hover styles
        "hover:bg-dark-700"
    )
    return this
}

fun <T : CommonAttributeGroupFacade> T.formSelectLabel(): T {
    classes = setOf(
        "form-label",
        "absolute",
        "left-0",
//        "top-2",
        "text-gray-500",
        "text-base",
        "font-light",
        "transition-all",
        "duration-300",
        "ease-in-out",
        "pointer-events-none"
    )
    return this
}

fun FlowContent.selectArrow() {
    iconSpan(icon = "keyboard_arrow_down", extraClasses = setOf("select-arrow"))
}

