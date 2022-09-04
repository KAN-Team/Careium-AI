## Careium-AI: Datasets

<!-- DATASETS: NUTRITION5K-->
### <a href="https://github.com/google-research-datasets/Nutrition5k">`Nutrition5K`</a>
According to <a href="https://github.com/google-research-datasets">Google research datasets</a>:
 - **Nutrition5k** is a dataset of visual and nutritional data for ~5k realistic plates of food.
 - **Nutrition5k** Scans data for 5,006 plates of food, each containing:
   - 4 rotating side-angle videos
   - Overhead RGB-D images (when available)
   - Fine-grained list of ingredients
   - Per-ingredient mass
   - Total dish mass and calories
   - Fat, protein, and carbohydrate macronutrient masses

<!-- DATASETS: FOOD101-->
### <a href="https://www.kaggle.com/dansbecker/food-101">`Food101`</a>
According to the <a href="https://data.vision.ee.ethz.ch/cvl/datasets_extra/food-101/static/bossard_eccv14_food-101.pdf">Food101 paper</a>,
**Food101** is a dataset of 101,000 real-world images in total:
  - 750 training images
  - 250 testing images
  - the training images were not cleaned, and thus still contain some amount of noise

![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/rainbow.png)

## Careium-AI: Models

<!-- MODELS: GENERAL GUIDELINES -->
#### General Guidelines & Preprocessing Techniques
- Uses **Nutrition5k** Dataset for Regression
- Uses **Food101** Dataset for Classification
- Train-Valid-Test split
  * Train Size => 2771
  * Valid Size => 380
  * Test Size => 360
- Target Size (24x24) and Batch Size (16)
- Training/Evaluation Metric
- Inputs/Outputs Normalization

<!-- MODELS: R1 TRIALS -->
#### R1 Trials & Outcomes
|   Model  | Test Loss | Cal Loss | Mass Loss | Fat Loss | Carb Loss | Prot Loss |
|:--------:|:---------:|:--------:|:---------:|:--------:|:---------:|:---------:|
|**1. ML Model**|**`0.8764`**|0.172|0.181|0.173|0.182|0.163|
|**2. Sequential DL Model**|**`0.7613`**|0.143|0.151|0.151|0.163|0.153|
|**3. Pre-trained MobileNet**|**`0.4768`**|0.112|0.094|0.092|0.087|0.091|
|**4. Pre-trained Inception-v3**|**`0.2957`**|0.076|0.064|0.061|0.052|0.042|

<p align="center">
  <img src="https://user-images.githubusercontent.com/48348589/188321356-8990010b-4355-45ae-bddd-3bdb0bc13804.jpg"/>
  <br/> 
  <i>Careium-AI R1 Architectures</i>
</p>

<!-- MODELS: C1 TRIALS -->
<!--
#### C1 Trials & Outcomes
|            | Base Model Accuracy | Optimized Model Accuracy |
|:----------:|:-------------------:|:------------------------:|
|**Training**|        61.23%       |       **`81.34%`**       |
|**Testing** |       [51%-68%]     |       **`78.50%`**       |

<p align="center">
  <img src="https://user-images.githubusercontent.com/48348589/188278329-08c4ddd9-0e96-4ce0-8feb-57d3c39627b8.png"/>
  <br/> 
  <i>Careium-AI C1 Performance</i>
</p>
-->

![-----------------------------------------------------](https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/rainbow.png)

> The Models trained with high score will be found [HERE](https://drive.google.com/drive/folders/1-ygaZ_p9w6zaEk_Ek8tgZMaoYYmoleVT).
