package com.github.h2020_5gtango.vnv.tee.tester.bash

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.stereotype.Component

@Component
@ConditionalOnExpression('"${sun.desktop}"=="windows"')
class WindowsRunner extends AbstractRunner {

    @Value('${app.tee.tester.bash.exe.path:C:\\Program Files\\Git\\bin\\bash.exe}')
    def bashExePath

    List<String> createCmd(File runnerSh) {
        [
                bashExePath,
                '-c',
                runnerSh.absolutePath.replaceAll('\\\\', '\\/'),
        ]
    }
}
