#should be in same folder, otherwise you should specify folder
with open('radixholder_final.txt', 'r') as f1:
  data1 = f1.read().split('\n')

with open('inuholder_final.txt', 'r') as f1:
  data2 = f1.read().split('\n')

#list sizes before changes
print(len(data1))
print(len(data2))

#sub
z = list(set(data1) - set(data2))

result = '\n'.join(z)
with open('readme.txt', 'w') as f:
    f.write(result)

#size of result
print(len(z))

#close streams
f.close()
f1.close()





