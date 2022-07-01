from tensorflow.keras.optimizers import Adam
from flask import Flask, request, jsonify
from tensorflow import keras
import numpy as np
import base64
import cv2
import re

# -------------------------
IMG_DIMN = 224
CALORIES_MAX = 9485.81543
MASS_MAX = 7975
FAT_MAX = 875.5410156
CARB_MAX = 844.5686035
PROTEIN_MAX = 147.491821
# -------------------------

app = Flask(__name__)


@app.route('/')
def home():
    return "Hello World!"


def convertImage(imgData):
    img_str = re.search(b'base64,(.*)', imgData).group(1)
    with open('input_image.png', 'wb') as output:
        output.write(base64.b64decode(img_str))


def preprocess_image(img_path):
    img = cv2.imread(img_path)
    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    img = cv2.resize(img, (IMG_DIMN, IMG_DIMN))
    img = (img / 255.0)
    return img


def load_model():
    model = keras.models.load_model('inceptionv3_saved_model.h5')
    model.compile(optimizer=Adam(lr=1e-5), loss='MSE')
    return model


def denormalize_outputs(outputs):
    cal = outputs[0] * CALORIES_MAX
    mass = outputs[1] * MASS_MAX
    fat = outputs[2] * FAT_MAX
    carb = outputs[3] * CARB_MAX
    prot = outputs[4] * PROTEIN_MAX
    return {'cal:': str(cal),
            'mass': str(mass),
            'fat': str(fat),
            'carb': str(carb),
            'prot': str(prot)}


@app.route('/predict', methods=['GET', 'POST'])
def predict():
    # convert kotlin imaga data into png image
    imgData = request.get_data()
    convertImage(imgData)

    # preprocess the image resize it to 224x224 and scale it by 1./255.0
    preprocessed_img = preprocess_image(img_path='input_image.png')

    # reshape to 4d nparray
    img_list = [np.array(preprocessed_img)]
    preprocessed_reshaped_img = np.asarray(img_list)

    # predict the image as numpy array
    model = load_model()
    outputs = model.predict(x=preprocessed_reshaped_img)

    # denormalize the outputs by multiplying with specified max value
    response = denormalize_outputs(outputs)
    return jsonify(response)


if __name__ == '__main__':
    app.run(debug=True)
