resource "aws_vpc" "main" {
  cidr_block           = "10.16.0.0/24"
  enable_dns_support   = true
  enable_dns_hostnames = true
  tags = {
    Name = "vpc-wedding-page"
  }
}

resource "aws_subnet" "web-az1" {
  vpc_id                  = aws_vpc.main.id
  availability_zone_id    = "eun1-az1"
  cidr_block              = "10.16.0.0/27"
  map_public_ip_on_launch = true
  tags = {
    Name = "web-az1"
  }
}

resource "aws_subnet" "app-az1" {
  vpc_id               = aws_vpc.main.id
  availability_zone_id = "eun1-az1"
  cidr_block           = "10.16.0.32/27"
  tags = {
    Name = "app-az1"
  }
}

resource "aws_subnet" "db-az1" {
  vpc_id               = aws_vpc.main.id
  availability_zone_id = "eun1-az1"
  cidr_block           = "10.16.0.64/27"
  tags = {
    Name = "db-az1"
  }
}

resource "aws_subnet" "web-az2" {
  vpc_id               = aws_vpc.main.id
  availability_zone_id = "eun1-az2"
  cidr_block           = "10.16.0.96/27"
  tags = {
    Name = "web-az2"
  }
}

resource "aws_subnet" "app-az2" {
  vpc_id               = aws_vpc.main.id
  availability_zone_id = "eun1-az2"
  cidr_block           = "10.16.0.128/27"
  tags = {
    Name = "app-az2"
  }
}

resource "aws_subnet" "db-az2" {
  vpc_id               = aws_vpc.main.id
  availability_zone_id = "eun1-az2"
  cidr_block           = "10.16.0.160/27"
  tags = {
    Name = "db-az2"
  }
}

resource "aws_internet_gateway" "main" {
  vpc_id = aws_vpc.main.id
  tags = {
    Name = "wedding-page-igw"
  }
}

resource "aws_route_table" "main" {

  vpc_id = aws_vpc.main.id
  route {
    cidr_block = aws_vpc.main.cidr_block
    gateway_id = "local"
  }
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.main.id
  }
  tags = {
    Name = "wedding-page-rt"
  }
}

resource "aws_route_table_association" "web-az1" {
  subnet_id      = aws_subnet.web-az1.id
  route_table_id = aws_route_table.main.id
}

resource "aws_route_table_association" "web-az2" {
  subnet_id      = aws_subnet.web-az2.id
  route_table_id = aws_route_table.main.id
}