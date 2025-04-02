package cz.pacmanplus

import ktx.app.KtxScreen
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.LoggerFactory
import java.lang.reflect.Type

class Navigator {

    val log = LoggerFactory.getLogger("Navigator")

    var onShowScreenFun: ((KtxScreen) -> Unit)? = null

    fun <T : KtxScreen> navigate(screen: T) {
        onShowScreenFun?.invoke(screen)
        log.debug("Navigate to screen ${screen::class.simpleName}")
    }

    fun initialize(func: (KtxScreen) -> Unit) {
        this.onShowScreenFun = func
        log.debug("Navigator initialized")
    }

    fun back() {

    }


}

fun KtxScreen.navigate(): Unit {
    getKoin().get<Navigator>().navigate(this)
}

fun KtxScreen.back(): Unit {
    getKoin().get<Navigator>().back()
}

