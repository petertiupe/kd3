@JsModule("d3")
@JsNonModule
//external fun <T> sorted(a: Array<T>): Boolean
external object d3 {
     fun select(selector: dynamic): Selection
}

external class Selection() {
     fun style(name: String, value: dynamic = definedExternally,  priority: dynamic = definedExternally)
}
