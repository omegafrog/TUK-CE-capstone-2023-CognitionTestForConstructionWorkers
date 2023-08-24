using System.Collections;
using System.Collections.Generic;
//using System.Data;
using UnityEngine;
using UnityEngine.SceneManagement;

public class Saw_tuto : MonoBehaviour
{
    public static bool counter;
    public static bool map_limit;
    public static bool game_end;

    public float speed;

    public bool up;
    public bool down;
    public bool left;
    public bool right;

    Vector3 lookDirection;

    void Start()
    {
        speed = 1.0f;
        counter = false;
        map_limit = false;
        game_end = false;
    }

    // Update is called once per frame
    void Update()
    {
        Tutorial_end();
    }

    void FixedUpdate()
    {
        Move();
        Rule();
    }

    public void Forward()
    {
        up = true;
    }
    public void Backward()
    {
        down = true;
    }
    public void Left()
    {
        left = true;
    }
    public void Right()
    {
        right = true;
    }
    public void stop()
    {
        up = false;
        down = false;
        right = false;
        left = false;
    }


    private void OnTriggerEnter(Collider other) //게임조건설정
    {
        if (other.tag == "wall")
        {
            counter = true;
            //Debug.Log("counter start");
        }
        if (other.tag == "saw_limit")
        {
            map_limit = true;
            //Debug.Log("map limit");
        }

        if (other.tag == "end_point") game_end = true;
    }

    void Move()
    {
        if (up == true) 
            transform.Translate(Vector3.forward * speed * Time.unscaledDeltaTime);
        
        if (down == true) 
            transform.Translate(Vector3.back * speed * Time.unscaledDeltaTime);
        
        if (left == true) 
            transform.Translate(Vector3.left * speed * Time.unscaledDeltaTime);
        
        if (right == true) 
            transform.Translate(Vector3.right * speed * Time.unscaledDeltaTime);
    }

    void Rule()
    {
        if (map_limit == true)
        {
            transform.position = new Vector3(-1.341f, 2.879f, -1.063f); //맵 밖으로 나가면 위치 초기화
            map_limit = false;
        }
        if (counter == true)
        {
            StartCoroutine(Trigger(3f));
            counter = false;
        }
        
    }

    void Tutorial_end()
    {
        if(game_end == true)
        {
            SceneManager.LoadScene("Main_menu", LoadSceneMode.Single);
        }
        if(Saw_tutoUI.playtime<= 0)
        {
            SceneManager.LoadScene("Main_menu", LoadSceneMode.Single);
        }
    }

    IEnumerator Trigger(float delay)
    {
        //Saw_tutoUI.playtime -= 3;
        Time.timeScale = 4;
        while (delay > 1.0f)
        {
            delay -= Time.deltaTime;
            yield return new WaitForFixedUpdate();
        }
        Time.timeScale = 1;
    }
}
