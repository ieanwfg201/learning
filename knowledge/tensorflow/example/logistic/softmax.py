import example.mnist.data_loader.input_data as input_data;
import tensorflow as tf

x = tf.placeholder("float", [None, 784])

if __name__ == '__main__':
    print