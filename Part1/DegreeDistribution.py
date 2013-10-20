import matplotlib.pyplot as plt

class DegreeDistribution:
	degreeDistribution = []
	def setDegreeDistribution(self, distribution):
		self.degreeDistribution = distribution
	def getDegreeDistribution(self):
		return self.degreeDistribution
	def plotDegreeDistribution(self, title='Degree Distribution', xlabel='Nodes', ylabel='Degrees'):
		x = []
		y = []
		for i, pair in enumerate(self.degreeDistribution):
			x += [pair[0]]
			y += [pair[1]]
			sys.stdout.write('Preparing: ' + str(100 * float(i) / float(len(self.degreeDistribution))) + '%         ')
			sys.stdout.flush()
			sys.stdout.flush()
			sys.stdout.write('\r')
		plt.plot(x, y, 'r+')
		plt.title(title)
		plt.xlabel(xlabel)
		plt.ylabel(ylabel)
		plt.show()
	def __init__(self, graph=None, dictionary=None, isAnonymized=None):
		if graph != None and dictionary != None and isAnonymized != None:
			lineNum = 1
			if isAnonymized:
				lineNum = 0
			degreeDistribution = []
			for i, line in enumerate(dictionary):
				degree = 0
				for edge in graph:
					if edge[0] == line[lineNum] or edge[1] == line[lineNum]:
						degree += 1
				sys.stdout.write('Finding Degree Distribution: ' + str(100 * float(i) / float(len(dictionary))) + '%         ')
				sys.stdout.flush()
				sys.stdout.flush()
				sys.stdout.write('\r')
				degreeDistribution += [(line[0], degree)]
			self.setDegreeDistribution(degreeDistribution)
		else:
			pass