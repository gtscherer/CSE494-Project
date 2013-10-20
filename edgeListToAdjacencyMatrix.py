import sys

anonymized = False

def searchEdgeList(edgeList, entry1, entry2):
	for line in edgeList:
		#print line
		if line[0] == entry1 and line[1] == entry2:
			return True
		elif line[1] == entry1 and line[0] == entry2:
			return True
	return False


def createVector(edgeList, dictionary, entry, anonymized):
	vector = []
	for line in dictionary:
		if anonymized:
			if searchEdgeList(edgeList, line[0], entry):
				vector += [1]
			else:
				vector += [0]
		else:		
			if searchEdgeList(edgeList, line[1], entry):
				vector += [1]
			else:
				vector += [0]
	return vector

def findInDictionary(dictionary, entry):
	if anonymized:
		return int(entry) - 1
	else:
		for line in dictionary:
			if entry == line[1]:
				return line[0] - 1
	return -1

def parseEdgeListToMatrix(matrix, dictionary, edgeList):
	for i, edge in enumerate(edgeList):
		index1 = findInDictionary(dictionary, edge[0])
		index2 = findInDictionary(dictionary, edge[1])
		#print "Found " + str(edge[0]) + " -> " + str(edge[1]) + " at [" + str(index1) + "] & [" + str(index2) + "]" 
 		matrix[index1][index2] = 1
		matrix[index2][index1] = 1
		sys.stdout.write("Progress: " + str(100 * float(i)/float(len(edgeList))) + "%        ")
		sys.stdout.flush()
		sys.stdout.flush()
		sys.stdout.write('\r')
	return matrix

if len(sys.argv) != 5:
	print 'Usage: python edgeListToAdjacencyMatrix.py /path/to/dictionary /path/to/dataset /path/to/output/file anonymized'
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
	matrix = []
	if sys.argv[4] == 'anonymized':
		anonymized = True
	iterations = 0
	dictionaryLength = len(parsedDictionary)
	matrix = []
	for i in xrange(dictionaryLength):
		vector = []
		for i in xrange(dictionaryLength):
			vector += [0]
		matrix += [vector]
		sys.stdout.write("Building empty matrix: " + str(100 * float(i)/float(dictionaryLength)) + "%                 ")
		sys.stdout.flush()
		sys.stdout.flush()
		sys.stdout.write('\r')
	'''
	# This method is extremely slow....
	for entry in parsedDictionary:
		if anonymized:
			vector = createVector(parsedDataset, parsedDictionary, entry[0], anonymized)
			matrix += [[vector]]
		else:
			vector = createVector(parsedDataset, parsedDictionary, entry[1], anonymized)
			matrix += [[vector]]
		iterations += 1
		percent = 100 * float(iterations)/float(dictionaryLength)
		print 'Progress: ' + percent.__str__() + '%'
	'''
	# New Method -> Undirected A=A^T
	matrix = parseEdgeListToMatrix(matrix, parsedDictionary, parsedDataset)
	for i, vector in enumerate(matrix):
		line = ''
		for j, num in enumerate(vector):
			if j == len(vector) - 1:
				line += str(num)
			else:
				line += str(num) + ','
		sys.stdout.write("Writing progress: " + str(100 * float(i)/float(len(matrix))) + "%                 ")
		sys.stdout.flush()
		sys.stdout.flush()
		sys.stdout.write('\r')
		output.write(line + '\n')