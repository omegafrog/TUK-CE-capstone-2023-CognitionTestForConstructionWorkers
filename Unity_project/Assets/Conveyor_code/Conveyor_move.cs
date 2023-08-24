using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Conveyor_move : MonoBehaviour
{
    /// <summary>
    /// 반응속도 테스트
    /// 유니티 deltaTime을 사용 60프레임 환경에서 기존 TOVA test 기준인 1ms까지 측정가능(최소측정값 0.016S)
    /// 게임시작시 12~100사이의 무작위 값으로 정해지는 변수(i)를 사용
    /// 변수값이 2 or 3의 배수인 경우 빨간색이 아닌 색상으로 오브젝트 변경
    /// 변수값이 소수인 경우 빨간색으로 오브젝트 변경
    /// 반응속도 측정은 button.cs코드확인
    /// </summary>

    // Start is called before the first frame update
    
    
    public int min;
    public int max;
    
    public static Renderer sr;
    public GameObject ob;
    
    public float object_speed;
    public int i;
    public static float timer;

    public bool object_tr;
    public static bool timer_start;

    void Start()
    {   
        sr = ob.GetComponent<Renderer>();
        min = 12;
        max = 100;
        //게임 시작시 무작위 값으로 초기화
        i = Random.Range(min, max); 
        timer = 0;
        object_speed = 4.0f;
        object_tr = false;
        timer_start = false;
    }

    void Update()
    {
        //Debug.Log(timer); //시간 시작확인
        Squence(i);
        Move();
        Timer();
        if(object_tr) Color_cg();
    }

    private void OnTriggerEnter(Collider other)
    {
        if (other.tag == "conveyor_dt")
        {
            transform.position = new Vector3(-4.274f, 1.26f, -1.788f);
            object_tr =true;
            i++;
        }
        if (other.tag == "conveyor_start") timer_start = true; //오브젝트 출발 확인

    }

    public void Timer() //반응속도 측정 타이머
    {
        timer = 0;
        if (timer_start)
        {
            timer += Time.deltaTime;
        }
    }

    void Move() //오브젝트 이동 제어
    {
         transform.Translate(Vector3.right * object_speed * Time.deltaTime);
    }

    void Color_cg() //오브젝트 컬러지정함수, 2와 3의배수 및 소수값에 따라 컬러가 정해진다.
    {
        if (i % 2 == 0) sr.material.color = Color.yellow;
        if (i % 3 == 0) sr.material.color = Color.blue;
        if(Squence(i)) sr.material.color = Color.red;
    }

    //소수 확인함수, i가 소수값인 경우 오브젝트를 빨간색으로 변경한다.
    public bool Squence(int num) 
    {
        for(int i = 2; i*i<=num; i++)
        {
            if (num % i == 0)
            {
                return false;
            } 
        }
        return true;
    }

}
