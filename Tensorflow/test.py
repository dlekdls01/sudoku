# 수정 및 주석 : webnautes
# 원본 코드 https://github.com/aymericdamien/TensorFlow-Examples/blob/master/examples/3_NeuralNetworks/neural_network_raw.py

from __future__ import print_function
import tensorflow as tf


#---------------------------------------------------------------------------------------------------- 1. MNIST 데이터를 가져옵니다.
# MNIST 데이터 관련 내용은 다음 포스팅 참고
#
# Tensorflow 예제 - MNIST 데이터 출력해보기 ( http://webnautes.tistory.com/1232 )
from tensorflow.examples.tutorials.mnist import input_data

# one_hot을 True로 설정하면 라벨을 one hot 벡터로 합니다. False이면 정수형 숫자가 라벨이 됩니다.
# /tmp/data/ 폴더를 생성하고 MNIST 데이터 압축파일을 다운로드 받아 압축을 풀고 데이터를 읽어옵니다.
# 이후에는 다운로드 되어있는 압축파일의 압축을 풀어서 데이터를 읽어옵니다.
mnist = input_data.read_data_sets("/data/", one_hot=True)

#---------------------------------------------------------------------------------------------------- 2. 파라미터 지정
# 뉴럴 네트워크 파라미터
n_hidden_1 = 256 # 첫번째 히든 레이어의 뉴런 개수
n_hidden_2 = 256 # 두번째 히든 레이어의 뉴런 개수
num_input = 784 # MNIST 데이터 입력( 28 x 28 이미지를 크기 784인 1차원 배열로 변환해서 사용)
num_classes = 10 # MNIST 데이터의 전체 클래스 개수 10개 ( 0 ~ 9 숫자 )

# 뉴럴 네트워크의 입력과 라벨
X = tf.placeholder(tf.float32, [None, num_input])
Y = tf.placeholder(tf.float32, [None, num_classes])

# 레이어(layer)의 weight와 bias를 저장할 변수를 선언합니다.
# 코드를 간결하게 하기 위해서 딕셔너리를 이용합니다.
weights = {
    'h1': tf.Variable(tf.random_normal([num_input, n_hidden_1])),
    'h2': tf.Variable(tf.random_normal([n_hidden_1, n_hidden_2])),
    'out': tf.Variable(tf.random_normal([n_hidden_2, num_classes]))
}
biases = {
    'b1': tf.Variable(tf.random_normal([n_hidden_1])),
    'b2': tf.Variable(tf.random_normal([n_hidden_2])),
    'out': tf.Variable(tf.random_normal([num_classes]))
}



#---------------------------------------------------------------------------------------------------- 3. 모델 생성
def neural_net(x):
    # Hidden fully connected layer with 256 neurons
    layer_1 = tf.add(tf.matmul(x, weights['h1']), biases['b1'])
    # Hidden fully connected layer with 256 neurons
    layer_2 = tf.add(tf.matmul(layer_1, weights['h2']), biases['b2'])
    # Output fully connected layer with a neuron for each class
    out_layer = tf.matmul(layer_2, weights['out']) + biases['out']
    return out_layer

logits = neural_net(X)
prediction = tf.nn.softmax(logits)


#---------------------------------------------------------------------------------------------------- 4.loss와 optimizer 정의
loss_op = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits_v2(logits=logits, labels=Y))
optimizer = tf.train.AdamOptimizer(learning_rate=0.0025).minimize(loss_op)      # 학습률 0.002 ~ 0.003 정도가 적당



# 정확도 측정할 모델
correct_prediction = tf.equal(tf.argmax(prediction, 1), tf.argmax(Y, 1))
accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))



with tf.Session() as sess:


    # 변수 초기화
    sess.run(tf.global_variables_initializer())

    #---------------------------------------------------------------------------------------------------------- 5. 훈련 시작
    for step in range(1, 5000+1):  # 변수 step은 1 ~ 1000 까지 값을 가지게 됨

        # 전체 훈련 데이터(mnist.train)에서 128개씩 데이터를 가져옵니다.
        batch_x, batch_y = mnist.train.next_batch(128)

        # optimizer 오퍼레이션을 실행합니다.
        sess.run(optimizer, feed_dict={X: batch_x, Y: batch_y})

        # 첫번째 훈련 후 그리고 100번 배수로 할때 마다 중간 결과 출력
        if step % 100 == 0 or step == 1:
            # Calculate batch loss and accuracy
            loss, acc = sess.run([loss_op, accuracy], feed_dict={X: batch_x, Y: batch_y})
            print("Step " + str(step) + ", Minibatch Loss= " + \
                  "{:.4f}".format(loss) + ", Training Accuracy= " + \
                  "{:.3f}".format(acc))

    print("최적화 완료")


    # 정확도 측정을 위해서 훈련 데이터(mnist.train) 대신에 별도의 테스트 데이터(mnist.test)를 사용해야 합니다.
    print("Testing Accuracy:", \
        sess.run(accuracy, feed_dict={X: mnist.test.images,
                                      Y: mnist.test.labels}))


    #---------------------------------------------------------------------------------------------------- 6. 모델 파라미터 저장
    model_path = "./model_mnist.ckpt" # 모델 파라미터를 저장할 경로와 파일 이름
    saver = tf.train.Saver()

    save_path = saver.save(sess, model_path)
    tf.train.write_graph(sess.graph_def, ".", 'trained.pb', as_text=False)
    tf.train.write_graph(sess.graph_def, ".", 'trained.txt', as_text=True)
    print("Model saved in file: %s" % save_path)
    
# CNN 레이아웃 모델    
    
# from tensorflow.examples.tutorials.mnist import input_data
# mnist = input_data.read_data_sets("./data/", one_hot=True)
# import tensorflow as tf
#
# x = tf.placeholder("float", shape=[None, 784])
# y_ = tf.placeholder("float", shape=[None, 10])
#
# x_image = tf.reshape(x, [-1,28,28,1])
# print ("x_image=")
# print (x_image)
#
# def weight_variable(shape):
#   initial = tf.truncated_normal(shape, stddev=0.1)
#   return tf.Variable(initial)
#
# def bias_variable(shape):
#   initial = tf.constant(0.1, shape=shape)
#   return tf.Variable(initial)
#
# def conv2d(x, W):
#   return tf.nn.conv2d(x, W, strides=[1, 1, 1, 1], padding='SAME')
#
# def max_pool_2x2(x):
#   return tf.nn.max_pool(x, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')
#
# W_conv1 = weight_variable([5, 5, 1, 32])
# b_conv1 = bias_variable([32])
#
# h_conv1 = tf.nn.relu(conv2d(x_image, W_conv1) + b_conv1)
# h_pool1 = max_pool_2x2(h_conv1)
#
# W_conv2 = weight_variable([5, 5, 32, 64])
# b_conv2 = bias_variable([64])
#
# h_conv2 = tf.nn.relu(conv2d(h_pool1, W_conv2) + b_conv2)
# h_pool2 = max_pool_2x2(h_conv2)
#
# W_fc1 = weight_variable([7 * 7 * 64, 1024])
# b_fc1 = bias_variable([1024])
#
# h_pool2_flat = tf.reshape(h_pool2, [-1, 7*7*64])
# h_fc1 = tf.nn.relu(tf.matmul(h_pool2_flat, W_fc1) + b_fc1)
#
# keep_prob = tf.placeholder("float")
# h_fc1_drop = tf.nn.dropout(h_fc1, keep_prob)
#
# W_fc2 = weight_variable([1024, 10])
# b_fc2 = bias_variable([10])
#
# y_conv=tf.nn.softmax(tf.matmul(h_fc1_drop, W_fc2) + b_fc2)
#
# cross_entropy = -tf.reduce_sum(y_*tf.log(y_conv))
# train_step = tf.train.AdamOptimizer(1e-4).minimize(cross_entropy)
# correct_prediction = tf.equal(tf.argmax(y_conv,1), tf.argmax(y_,1))
# accuracy = tf.reduce_mean(tf.cast(correct_prediction, "float"))
#
# sess = tf.Session()
#
# sess.run(tf.global_variables_initializer())
#
# for i in range(600):
#    batch = mnist.train.next_batch(50)
#    if i%10 == 0:
#      train_accuracy = sess.run( accuracy, feed_dict={ x:batch[0], y_: batch[1], keep_prob: 1.0})
#      print("step %d, training accuracy %g"%(i, train_accuracy))
#    sess.run(train_step,feed_dict={x: batch[0], y_: batch[1], keep_prob: 0.5})
#
# print("test accuracy %g"% sess.run(accuracy, feed_dict={
#        x: mnist.test.images, y_: mnist.test.labels, keep_prob: 1.0}))
#
# path = "./model_cnn.ckpt" # 모델 파라미터를 저장할 경로와 파일 이름
# saver = tf.train.Saver()
#
# save_path = saver.save(sess, path)
# print("Model saved in file: %s" % save_path)