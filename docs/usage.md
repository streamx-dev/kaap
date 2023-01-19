# Usage
#### Helm deployment (tested with K8s 1.25)

Install the stack operator:
```
export ns=mypulsar
helm dependency build helm/pulsar-stack
helm install -n $ns --create-namespace pulsar helm/pulsar-stack
```

Install a Pulsar cluster Custom Resource
```
kubectl -n $ns apply -f helm/examples/cluster.yaml

```

Wait for the cluster to be up and running
```
kubectl wait pulsar -n $ns pulsar-cluster --for condition=Ready=True --timeout=240s
```

Uninstall the cluster
```
kubectl -n $ns delete PulsarCluster pulsar-cluster
```

Uninstall the operator and the CRDs
```
helm delete pulsar -n $ns
```

### Setup token authentication

Enable authentication on the cluster. Secrets for super user roles are automatically generated by the operator.
```
global:
  auth:
    enabled: true
```

Generate a token for a subject, give them some permissions and produce a message using it.
```
PULSAR_TOKEN=$(kubectl exec deployment/pulsar-bastion -- bin/pulsar tokens create --private-key token-private-key/my-private.key --subject myuser)
echo $PULSAR_TOKEN
kubectl exec deployment/pulsar-bastion -- bin/pulsar-shell -e 'admin namespaces grant-permission --role myuser --actions produce,consume public/default'
kubectl exec deployment/pulsar-bastion -- bin/pulsar-shell -e "client --auth-params \"token:$PULSAR_TOKEN\" produce -m hello public/default/topic"
```

### Grafana

See the [Grafana example](../helm/examples/grafana).


### Keycloak

See the [Keycloak example](../helm/examples/keycloak).