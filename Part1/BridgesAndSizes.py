from queue import Queue
from random import choice

def bfs(item1, item2, edgeList):
	q = Queue(maxsize=0)
	q.put(item1)
	while(not q.empty()):
		parent = q.get()
		for edge in edgeList:
			if edge[0] == parent:
				if edge[1] == item2:
					return True
				else: 
					q.put(edge[1])
	return False

def edgeRemover(edge, edgeList):
	newList = []
	for line in edgeList:
		if line[0] == edge[0] and line[1] == edge[1]:
			continue
		else:
			newList += [line]
	return newList

class PercentageSizeRemover:
	edgeList = []
	def getPercentage(self, percent):
		num = int(percent / len(edgeList))
		removeList = []
		for i in xrange(len(self.edgeList):
			removeList += [choice(self.edgeList)]
		return removeList
	def removeEdges(self, removeList):
		augmentedList = self.edgeList
		for edge in removeList:
			augmentedList = edgeRemover(edge, augmentedList)
		return augmentedList
	def __init__(self, edgeList):
		self.edgeList = edgeList

class BridgeFinder:
	edgeList = []
	bridgeList = []
	numBridges = 0
	def getNumBridges(self):
		return self.numBridges
	def setNumBridges(self, num):
		self.numBridges = num
	def findBridges(self):
		for i,edge in enumerate(edgeList):
			newEdgeList = edgeRemover(edge, self.edgeList)
			if bfs(edge[0], edge[1], newEdgeList):
				continue
			else:
				self.bridgeList += [edge]
			sys.stdout.write('Finding Bridges: ' + str(100 * float(i) / float(len(edgeList))) + '%         ')
			sys.stdout.flush()
			sys.stdout.flush()
			sys.stdout.write('\r')
		self.setNumBridges(len(bridgeList))
		return self.bridgeList
	def __init__(self, edgeList):
		self.edgeList = edgeList