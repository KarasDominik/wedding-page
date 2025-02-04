provider "aws" {
  region = "eu-north-1"
}

resource "aws_instance" "main" {
  ami                         = "ami-087fba4aa07ebd20f"
  instance_type               = "t3.micro"
  subnet_id                   = aws_subnet.web-az1.id
  security_groups = [aws_security_group.ec2-security-group.id]
  key_name                    = aws_key_pair.ec2-key-pair.key_name
  tags = {
    Name = "wedding-page"
  }
}

resource "aws_key_pair" "ec2-key-pair" {
    key_name   = "wedding-page-key-pair"
    public_key = file("~/.ssh/id_rsa.pub")
}

resource "aws_security_group" "ec2-security-group" {
  vpc_id = aws_vpc.main.id

  ingress {
    from_port = 80
    to_port   = 80
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port = 22
    to_port   = 22
    protocol  = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "wedding-page-ec2-sg"
  }
}

resource "aws_vpc" "main" {
  cidr_block           = "10.16.0.0/24"
  enable_dns_support   = true
  enable_dns_hostnames = true
  tags = {
    Name = "vpc-wedding-page"
  }
}

resource "aws_subnet" "web-az1" {
  vpc_id               = aws_vpc.main.id
  availability_zone_id = "eun1-az1"
  cidr_block           = "10.16.0.0/27"
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

resource "aws_db_subnet_group" "main" {
  name       = "wedding-page-db-subnet-group"
  subnet_ids = [aws_subnet.db-az1.id, aws_subnet.db-az2.id]

  tags = {
    Name = "wedding-page-db-subnet-group"
  }
}

resource "aws_security_group" "db-security-group" {
  vpc_id = aws_vpc.main.id

  ingress {
    from_port = 5432
    to_port   = 5432
    protocol  = "tcp"
    security_groups = [aws_security_group.ec2-security-group.id]
  }
}

resource "aws_db_instance" "main" {
  allocated_storage = 20
  db_name           = "weddingpagedb"
  engine            = "postgres"
  engine_version    = "16.3"
  instance_class    = "db.t4g.micro"
  username          = "postgres"
  password          = "password"
  db_subnet_group_name = aws_db_subnet_group.main.name
  vpc_security_group_ids = [aws_security_group.db-security-group.id]
  tags = {
    Name = "wedding-page-db"
  }
}