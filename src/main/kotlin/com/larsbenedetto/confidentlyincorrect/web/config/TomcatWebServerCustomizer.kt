package com.larsbenedetto.confidentlyincorrect.web.config

import groovy.util.logging.Slf4j
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.catalina.valves.AbstractAccessLogValve
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.stereotype.Component
import java.io.CharArrayWriter

@Component
class TomcatWebServerCustomizer : WebServerFactoryCustomizer<TomcatServletWebServerFactory?> {

  override fun customize(factory: TomcatServletWebServerFactory?) {
    val accessLogValve = AccessLog4j2Valve()
    accessLogValve.setPattern("%a %t %{ms}T \"%r\" %s %b \"%{User-Agent}i\"")
    accessLogValve.setRequestAttributesEnabled(true)
    factory!!.addEngineValves(accessLogValve)
  }
}

@Slf4j
class AccessLog4j2Valve : AbstractAccessLogValve() {
  private val log: KLogger = KotlinLogging.logger {}

  public override fun log(msg: CharArrayWriter?) {
    msg?.let { log.info { it } }
  }
}
