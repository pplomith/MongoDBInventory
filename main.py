import pandas as pd

# PREPROCESSING STEPS
from tabulate import tabulate

data = pd.read_csv("/Users/emanuelefittipaldi/PycharmProjects/AmazonProductsPreprocessing/marketing_sample_for_amazon_com-ecommerce__20200101_20200131__10k_data.csv")

# DROP DELLE COLONNE CHE NON MI SERVONO PERCHE' CONTENGONO SOLO VALORI NULLI
data=data.drop(['Brand Name'],axis=1)
data=data.drop(['Asin'],axis=1)
data=data.drop(['Upc Ean Code'],axis=1)
data=data.drop(['List Price'],axis=1)
data=data.drop(['Quantity'],axis=1)
data=data.drop(['Model Number'],axis=1)
data=data.drop(['Product Specification','Product Dimensions','Variants','Sku','Stock','Product Details','Dimensions','Color','Ingredients','Direction To Use','Is Amazon Seller','Product Description','Size Quantity Variant'],axis=1)

# CONSERVO SOLO LE RIGHE CHE NON SONO NULLE DELLE SEGUENTI CATEGORIE
data = data[pd.notnull(data['Category'])]
data = data[pd.notnull(data['About Product'])]
data = data[pd.notnull(data['Technical Details'])]
data = data[pd.notnull(data['Shipping Weight'])]

# SLICING
data = data.iloc[:500,:]

# PREPROCESSING DELLE IMMAGINI
immagini = []
for row in data['Image']:
    immagini.append((row.split('|'))[0])
data['Image'] = immagini


# PREPROCESSING DELLA CATEGORIA
cat = []
for row in data['Category']:
    temp = row.split('|')
    cat.append(temp[0])
data['Category'] = cat



# PREPROCESSING DEL PREZZO
price = []
for row in data['Selling Price']:
    temp = row.split('$')
    if len(temp)==1:
        price.append((10.00 * 0.7846))
    elif '-' in temp[1]:
        temp = temp[1].replace('-','')
        temp = float(temp)
        price.append((temp * 0.7846))
    elif ',' in temp[1]:
        temp = temp[1].replace(',','')
        price.append((float(temp)* 0.7846))
    else:
        price.append((float(temp[1])* 0.7846)) # conversione in euro
data['Selling Price'] = price
data = data.round({'Selling Price': 2})

about = []
for row in data['About Product']:
    temp = row.split('|')
    uniqueString = ''
    for sentence in temp:
        uniqueString = uniqueString+sentence
    about.append(uniqueString)

data['About Product'] = about

tech = []
for row in data['Technical Details']:
    temp = row.split('|')
    uniqueString = ''
    for sentence in temp:
        uniqueString = uniqueString+sentence
    tech.append(uniqueString)

data['Technical Details'] = tech

weight = []
for row in data['Shipping Weight']:
    if 'pounds' in row:
        temp = row.replace('pounds','')
        temp = float(temp)* 0.4536
        weight.append(temp)
    elif 'ounces' in row:
        temp = row.replace('ounces','')
        temp = float(temp)/35.2739
        weight.append(temp)
data['Shipping Weight'] = weight
data = data.round({'Shipping Weight': 2})







data.to_csv("/Users/emanuelefittipaldi/PycharmProjects/AmazonProductsPreprocessing/ProductsDataset.csv",index=False)

