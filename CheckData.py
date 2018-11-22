import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

import numpy as np
import matplotlib.pyplot as plt

# Import MNIST data
from tensorflow.examples.tutorials.mnist import input_data
# 데이터를 다운받고 경로를 지정해줄 것
mnist = input_data.read_data_sets("/data/", one_hot=True)

print("훈련 이미지 :",  mnist.train.images.shape)
print("훈련 라벨:",  mnist.train.labels.shape)
print("테스트 이미지 : ", mnist.test.images.shape)
print("테스트 라벨 : ", mnist.test.labels.shape)
print("검증 이미지 : ", mnist.validation.images.shape)
print("검증 라벨 : ", mnist.validation.labels.shape)
print('\n')

# 불러온 이미지의 인덱스, 인덱스 숫자 설정
mnist_idx = 106


print('[label]')
# 0 ~ 9 까지 one-hot 라벨
print('one-hot vector label = ', mnist.train.labels[mnist_idx])
# 해당 번호 라벨
print('number label = ', np.argmax(mnist.train.labels[mnist_idx]))
print('\n')

print('[image]')

# 픽셀에 따른 수치값 출력
for index, pixel in enumerate(mnist.train.images[mnist_idx]):
    if index % 28 == 0:
        print('\n')
    else:
        print("%.3f " % pixel, end="")
print('\n')


plt.figure(figsize=(5, 5))
image = np.reshape(mnist.train.images[mnist_idx], [28, 28])
plt.imshow(image, cmap='Greys')
plt.show()