import sys

if len(sys.argv) != 4:
	print 'Usage: python anonymizer.py /path/to/dictionary /path/to/dataset /path/to/output/file'
else:
	dictionary = open(sys.argv[1])
	dataset = open(sys.argv[2])
	output = open(sys.argv[3], "w")
	parsedDictionary = []
	parsedDataset = []
	for line in dictionary:
		first = ''
		second = ''
		comma = 0
		for char in line:
			#print char
			if char != ',':
				if char != '\n':
					if comma > 0:
						second += char
					else:
						first += char
			else:
				comma = 1
		parsedDictionary += [[ first , second ]]
	for line in dataset:
		first = ''
		second = ''
		comma = 0
		for char in line:
			if char != ',':
				if char != '\n':
					if comma > 0:
						second += char
					else:
						first += char
			else:
				comma = 1
		parsedDataset += [[ first , second ]]
	anonymizedSet = []
	for datum in parsedDataset:
		#print datum
		foundFirst = 0
		foundSecond = 0
		first = ''
		second = ''
		for entry in parsedDictionary:
			#print entry
			if entry[1] == datum[0]:
				first = entry[0]
				foundFirst += 1
			elif entry[1] == datum[1]:
				second = entry[0]
				foundSecond += 1
			if foundFirst > 0 and foundSecond > 0:
				#print first + ' , ' + second
				break
		anonymizedSet += [[first, second]]
	for line in anonymizedSet:
		#print line
		output.write(line[0] + ',' + line[1] + '\n')