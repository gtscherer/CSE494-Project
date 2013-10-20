import networkx as nx

class GraphFactory:
	dataSet = []
	dictionary = []
	graph = nx.Graph()
	def getDataSet(self):
		return self.dataSet
	def setDataSet(self, dataSet):
		self.dataSet = dataSet
	def getDictionary(self):
		return self.dictionary
	def setDictionary(self, dictionary):
		self.dictionary = dictionary
	def setGraph(self, graph):
		self.graph = graph
	def getGraph(self):
		return self.graph
	def __init__(self, dataSetPath, dictionaryPath):
		dictionary = open(dictionaryPath)
		dataSet = open(dataSetPath)
		parsedDictionary = []
		parsedDataset = []
		iterations = float(0)
		size = float(len(dictionary) + len(dataset))
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
			parsedDictionary += [(first , second)]
			iterations += 1
			sys.stdout.write('Parsing Data (Dictionary): ' + str(100 * iterations / size) + '%         ')
			sys.stdout.flush()
			sys.stdout.flush()
			sys.stdout.write('\r')
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
			parsedDataset += [(first , second)]
			iterations += 1
			sys.stdout.write('Parsing Data (Data Set): ' + str(100 * iterations / size) + '%         ')
			sys.stdout.flush()
			sys.stdout.flush()
			sys.stdout.write('\r')
		self.setDictionary(parsedDictionary)
		self.setDataSet(parsedDataset)
		self.getGraph().add_edges_from(self.getDataSet())

		
