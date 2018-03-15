export
date
echo 'docker run --rm -v ${workspace.absolutePath}:/workspace registry.sonata-nfv.eu:5000/tng-vnv-tester-ttcn3 ttcn3_start /workspace/test-suite-binary /workspace/config'
echo 'SUCCESS'