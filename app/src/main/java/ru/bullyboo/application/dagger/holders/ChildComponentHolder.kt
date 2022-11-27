package ru.bullyboo.astrology.dagger.holders

abstract class ChildComponentHolder <ParentComponent, Component>(
    private val parentHolder: ComponentHolder<ParentComponent>
) : ComponentHolder<Component> {

    private var component: Component? = null

    override fun provide(): Component {
        synchronized(this){

            if(component == null){
                component = build(parentHolder.provide())
            }

            return component!!
        }
    }

    abstract fun build(parent: ParentComponent): Component

    open fun destroy() {
        component = null
    }
}