package ru.bullyboo.core_ui.managers

import androidx.appcompat.app.AppCompatActivity

object BackStackManager {

    /**
     * Метод, реализующий переход назад с учетом стека фрагментов
     *
     * ******** !!! ********
     * Обязательно писать комментарии на каждое действие перехода назад и проверку,
     * так как алгоритм имеет очень большое количество нюансов, токностей и подвержен
     * фрагментации на разных устройствах
     * ******** !!! ********
     */
    fun onBackPressed(activity: AppCompatActivity) {
        with(activity.supportFragmentManager) {

//            если backStackEntryCount == 0 и список fragments пустой,
//            значит на экране ничего нет и можно с уверенностью закрывать activity
            if (backStackEntryCount == 0 && fragments.size == 0) {
                activity.finish()
                return
            }

//            если backStackEntryCount == 0 и список fragments НЕ пустой
//            эта проверка нужна для проверки childFragmentManager у рутового фрагмента
            if (fragments.size > 0) {

//                находим фрагмент, который на текущий момент находится на переднем плане
                val frontFragment = fragments.firstOrNull { it.isVisible }

                if (frontFragment == null) {

//                    в случае, если не удалось найти фрагмент, который на текущий момент
//                    находится на переднем плане, дропаем активити, так как скорее всего
//                    какой то фрагмент залип в стеке, так делают некоторые библиотеки
                    activity.finish()
                } else {
                    val backStackChildManager =
                        frontFragment.childFragmentManager.backStackEntryCount

                    if (backStackChildManager == 0) {

//                        если у фрагмента, находящегося на переднем плане, у childFragmentManager
//                        нет никаких фрагментов в стэке, и:
                        if (backStackEntryCount == 0) {

//                        у основного менеджера активити пустой стек фрагмента, закрываем активити
                            activity.finish()
                        } else {

//                        у основного менеджера есть фрагменты в стеке, закрываем текущий фрагмент
                            popBackStack()
                        }
                    } else {

//                        если у фрагмента, находящегося на переднем плане, у childFragmentManager
//                        есть фрагменты в стэке
                        frontFragment.childFragmentManager.popBackStack()
                    }
                }
            } else {

//                сюда мы попадаем в том случае, если backStackEntryCount НЕ равен 0, но список
//                фрагментов у менеджера пустой, такая ситуация может быть только в том случае,
//                если в менеджер добавлялись фрагменты без флага addToBackStack(...),
//                в таком случае вызов метода popToBackStack() не даст никакого результата,
//                поэтому просто дропаем активити
                activity.finish()
            }

            return
        }
    }
}