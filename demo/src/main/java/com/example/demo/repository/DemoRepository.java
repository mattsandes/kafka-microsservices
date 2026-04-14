package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.data.Clients;

public interface DemoRepository extends JpaRepository<Clients, UUID>{}
