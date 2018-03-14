package eu.h2020_5gtango.vnv.tee.tester.bash

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.stereotype.Component

@Component
@ConditionalOnExpression('"${sun.desktop}"!="windows"')
class LinuxRunner extends AbstractRunner {

    List<String> createCmd(File runnerSh) {
        [
                'sh',
                '-c',
                runnerSh.absolutePath,
        ]
    }
}
