# huffman-image-compression
Huffman Image Compression is one of the basic compression methods, that have proven useful in image and video compression standards. When applying Huffman encoding technique on an Image, the source symbols can be either pixel intensities of the Image, or the output of an intensity mapping function.

# Usage
git clone https://github.com/archetana/huffman-image-compression.git
cd huffman-image-compression
ant 
cd dist
java -cp HuffmanCoding.jar huffmancoding.HuffmanCoding <abosolute path to image to be compressed>