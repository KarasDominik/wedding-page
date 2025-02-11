resource "aws_instance" "main" {
  ami                         = "ami-087fba4aa07ebd20f"
  instance_type               = "t3.micro"
  subnet_id                   = aws_subnet.web-az1.id
  security_groups             = [aws_security_group.ec2-security-group.id]
  associate_public_ip_address = true
  key_name                    = "wedding-page-key-pair"
  tags = {
    Name = "wedding-page"
  }
}

resource "aws_key_pair" "ssh-key" {
  key_name   = "wedding-page-key-pair"
  public_key = file("~/.ssh/id_rsa.pub")
}

resource "aws_security_group" "ec2-security-group" {
  vpc_id = aws_vpc.main.id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
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