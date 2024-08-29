# Getting Started

Simple Application to demonstrate Ratelimit using Istio

### Build the Image
```
./gradlew clean build  
docker build -t hello-istio:1.0 .  
docker run --rm -p 8080:8080  hello-istio:1.0 
curl  http://localhost:8080/hello ( in other window ) 
```
### Prepare Minikube

```
minikube start

minikube addons enable istio-provisioner
minikube addons enable istio

 kubectl get pod -n istio-system
 
```

### Deploy in K8s 


```commandline

minikube image load hello-istio:1.0 

cd deployment 
kubectl apply -f deployment.yaml 

kubectl get pods

minikube tunnel
kubectl apply -f service.yaml 

Now you should be able to check k8s service
curl  http://localhost:8080/hello

```

### Test without Rate limit using Postman
![postman-collection](https://github.com/user-attachments/assets/f58845b9-31c2-41de-872c-5d4b84c9b500)

![postman-runner](https://github.com/user-attachments/assets/86717b9e-aa1d-4534-8d5c-7e6e975d2eea)

Create a Collection say "Containers" with just one Request for
http://localhost:8080/hello

Test add this script 
```commandline
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

```
Its successful when the response is 200
Run  the test with 20 request and validate if _all_ are successful 

### Apply Envoy filter

```commandline
kubectl label namespace default istio-injection=enabled
kubectl apply -f envoyfilter.yaml
kubectl exec -it podname -c istio-proxy -- pilot-agent request GET config_dump

```
### Test Ratelimit 
```commandline
kubectl apply -f envoyfilter.yaml

```
![before-ratelimit](https://github.com/user-attachments/assets/8ef1c2a1-3320-457a-bd0e-827e2d7e70ba)

### Test With Rate limit
Run  the test with 20 request and validate _only 10_ of them have succeeded
![after-ratelimit](https://github.com/user-attachments/assets/b33eaacc-9c36-4f26-8262-39d26e0e66ea)

### Clean up 

```commandline

kubectl delete deployment helloistio
kubectl delete service helloistio-service
minikube stop 
Also close the minikube tunnel 
```
