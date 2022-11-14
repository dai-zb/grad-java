```python
import torch
```

## 验证5

Y = X W

Z = Y + B

l = MS(Z - ZZ)


```python
X = torch.tensor([[4,2,3],[1,4,3],[1,2,5]], dtype=torch.float32)
W = torch.tensor([[3,2,5],[1,9,3],[7,2,1]], dtype=torch.float32)
B = torch.tensor([[1,0,1],[0,1,0],[1,0,1]], dtype=torch.float32)

X.requires_grad_(requires_grad=True)
W.requires_grad_(requires_grad=True)
B.requires_grad_(requires_grad=True)
```




    tensor([[1., 0., 1.],
            [0., 1., 0.],
            [1., 0., 1.]], requires_grad=True)




```python
ZZ = torch.tensor([[1,0,0],[0,1,0],[1,0,0]], dtype=torch.float32)
```


```python
Y=torch.mm(X, W)
Y
```




    tensor([[35., 32., 29.],
            [28., 44., 20.],
            [40., 30., 16.]], grad_fn=<MmBackward>)




```python
Z=Y+B
Z
```




    tensor([[36., 32., 30.],
            [28., 45., 20.],
            [41., 30., 17.]], grad_fn=<AddBackward0>)




```python
l=((Z-ZZ)**2).sum()/3
l
```




    tensor(3019.3333, grad_fn=<DivBackward0>)




```python
l.backward()
```


```python
X.grad
```




    tensor([[212.6667, 275.3333, 226.0000],
            [181.3333, 322.6667, 202.6667],
            [176.6667, 240.6667, 238.0000]])




```python
W.grad
```




    tensor([[138.6667, 134.6667, 104.6667],
            [174.6667, 200.0000, 116.0000],
            [259.3333, 252.0000, 156.6667]])




```python
B.grad
```




    tensor([[23.3333, 21.3333, 20.0000],
            [18.6667, 29.3333, 13.3333],
            [26.6667, 20.0000, 11.3333]])


