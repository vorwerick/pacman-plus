package cz.pacmanplus

import com.badlogic.gdx.ApplicationAdapter
import cz.pacmanplus.di.accessories
import cz.pacmanplus.di.screenFactory
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject
import org.slf4j.LoggerFactory

class ApplicationCore : ApplicationAdapter() {
    val log = LoggerFactory.getLogger("ApplicationCore")
    val rootUi: RootUI by inject(RootUI::class.java)

    override fun create() {
        log.debug("App started")

        startKoin {
            modules(listOf(accessories, screenFactory))
            log.debug("Koin initialized")
        }
        rootUi.create()
    }

    override fun resize(p0: Int, p1: Int) {
        rootUi.resize(p0, p1)
    }

    override fun render() {
        rootUi.render()
    }

    override fun pause() {
        rootUi.pause()
    }

    override fun resume() {
        rootUi.resume()
    }

    override fun dispose() {
        rootUi.dispose()
    }
}
