package ru.bullyboo.astrology.dagger.holders

abstract class ParentComponentHolder<Component> : ComponentHolder<Component> {

    private var component: Component? = null

    override fun provide(): Component {
        synchronized(this){

            if(component == null){
                component = build()
            }

            return component!!
        }
    }

    abstract fun build(): Component

    open fun destroy(){
        component = null
    }
}