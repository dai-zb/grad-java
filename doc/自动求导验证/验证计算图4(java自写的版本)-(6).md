#计算图

## 验证6

Y = softmax(X)

l = CE(Y,Y_bar)


```python
import torch
import torch.nn as nn
```

### 输入&输出


```python
x = torch.randn(5, 3)
x.requires_grad_(requires_grad=True)
```




    tensor([[-0.2743,  0.3708,  0.3835],
            [ 1.7228, -0.2395,  0.3428],
            [-1.2263,  0.7666, -0.1894],
            [ 0.5035, -0.4118, -0.7194],
            [-0.9220, -1.9626, -0.5345]], requires_grad=True)




```python
x = torch.tensor(
       [[ 0.4469,  0.7072,  1.2886],
        [ 0.0334, -1.6490, -0.8464],
        [ 1.7588,  0.2174,  1.6029],
        [-0.6278, -1.3048, -0.6514],
        [-0.8362, -0.5904,  0.2429]])
x.requires_grad_(requires_grad=True)
```




    tensor([[ 0.4469,  0.7072,  1.2886],
            [ 0.0334, -1.6490, -0.8464],
            [ 1.7588,  0.2174,  1.6029],
            [-0.6278, -1.3048, -0.6514],
            [-0.8362, -0.5904,  0.2429]], requires_grad=True)




```python
target=torch.tensor([2,1,0,2,1])
target
```




    tensor([2, 1, 0, 2, 1])




```python
softmax1=torch.nn.Softmax(dim=1)
softmax1(x)
```




    tensor([[0.2166, 0.2809, 0.5025],
            [0.6247, 0.1161, 0.2592],
            [0.4832, 0.1034, 0.4134],
            [0.4024, 0.2045, 0.3931],
            [0.1915, 0.2449, 0.5635]], grad_fn=<SoftmaxBackward>)



### 构建计算


```python
loss = nn.CrossEntropyLoss()
l=loss(x,target)
l
```




    tensor(1.1818, grad_fn=<NllLossBackward>)



### 自动求导计算梯度


```python
l.backward()
x.grad
```




    tensor([[ 0.0433,  0.0562, -0.0995],
            [ 0.1249, -0.1768,  0.0518],
            [-0.1034,  0.0207,  0.0827],
            [ 0.0805,  0.0409, -0.1214],
            [ 0.0383, -0.1510,  0.1127]])


