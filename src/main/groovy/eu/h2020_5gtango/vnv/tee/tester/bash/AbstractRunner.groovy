package eu.h2020_5gtango.vnv.tee.tester.bash

abstract class AbstractRunner {

    abstract List<String> createCmd(File runnerSh)

    Map exexuteSh(File runnerSh, def ouputScanner = null) {
        def result = [exitValue: -1, stout: new StringBuffer(), sterr: new StringBuffer()]
        Process process = new ProcessBuilder(createCmd(runnerSh)).start()
        def inputStreamThread = consumeStream(process.inputStream, result.stout, ouputScanner, result)
        def errorStreamThread = consumeStream(process.errorStream, result.sterr, ouputScanner, result)
        try {
            result.exitValue = process.waitFor()
            inputStreamThread.join()
            errorStreamThread.join()
        } catch (Exception e) {
            e.printStackTrace()
        }
        result
    }

    Thread consumeStream(InputStream stream, StringBuffer output, def ouputScanner, def result) {
        Thread.start {
            stream.eachLine { line ->
                println line
                output.append(line)
                output.append('\n')
                if (ouputScanner) {
                    ouputScanner(line, result)
                }
            }
        }
    }
}
