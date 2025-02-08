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
    from_port       = 5432
    to_port         = 5432
    protocol        = "tcp"
    security_groups = [aws_security_group.ec2-security-group.id]
  }
}

resource "aws_db_instance" "main" {
  allocated_storage      = 20
  db_name                = "weddingpagedb"
  engine                 = "postgres"
  engine_version         = "16.3"
  instance_class         = "db.t4g.micro"
  username               = "postgres"
  password               = "password"
  db_subnet_group_name   = aws_db_subnet_group.main.name
  vpc_security_group_ids = [aws_security_group.db-security-group.id]
  tags = {
    Name = "wedding-page-db"
  }
}