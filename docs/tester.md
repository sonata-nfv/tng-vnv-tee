## Test Execution Engine (TEE) tester support

### bash tester
TEE support bash base script test. You need set `test_type=bash` to active the bash tester.

The bash tester will execute `runner.sh` in package root folder. Test result will be marked based on the script exit code:
* none 0 value = FAIlED
* 0 = SUCCESS

## TTCN3 tester

The TTCN3 tester was a special type of bash tester. You need set `test_type=ttcn3` to active the tester.

The TTCN3 tester will execute `runner.sh` in package root folder. Test result will be marked based on following condition:
* none 0 value = ERROR
* has TTCN3 tests fail = FAILED
* has TTCN3 tests success = SUCCESS
* has NONE TTCN3 tests success = INVALID_TEST_RESULT
