export
date
echo 'docker run --rm -v tee:/workspace registry.sonata-nfv.eu:5000/tng-vnv-tester-ttcn3 ttcn3_start ${workspace.absolutePath}/test-suite-binary ${workspace.absolutePath}/config'
echo 'SUCCESS'