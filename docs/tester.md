## Test Execution Engine (TEE) tester support

### bash tester
TEE support bash base script test. You need set `test_type=bash` to active the bash tester.

The bash tester will execute `runner.sh` in package root folder. Test result will be marked based on the script exit code:
* none 0 value = FAIlED
* 0 = SUCCESS

## TTCN3 tester

The TTCN3 tester was a special type of bash tester. You need set `test_type=ttcn3` to active the tester.

The TTCN3 tester will execute `runner.sh` in package root folder. You could use following sample command as template:
```sh
docker run --rm -v ${workspace.absolutePath}:/workspace registry.sonata-nfv.eu:5000/tng-vnv-tester-ttcn3 ttcn3_start /workspace/test-suite-binary /workspace/config
```

Test result will be marked based on following condition:
* none 0 value = ERROR
* has TTCN3 tests fail = FAILED
* has TTCN3 tests success = SUCCESS
* has NONE TTCN3 tests success = INVALID_TEST_RESULT

The test result summary will be stored at `testSuiteResult.details` e.g.
```json
{
    "test_suite_result_id":"test_suite_result_id",
    "test_suite_id":"test_suite_id",
    "details":
        {
          "none":0,
          "pass":19,
          "inconc":0,
          "fail":3,
          "error":0
        },
    "status":"FAILED"
}
```
